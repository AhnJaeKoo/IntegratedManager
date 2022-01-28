package com.enuri.integratedmanager.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import lombok.extern.slf4j.Slf4j;

/**
  * @description : Ftp 연결하여 runCommand로 원격명령 실행
  * @Since : 2020. 11. 4
  * @Author : AnJaeKoo
  * @History :
  */
@Slf4j
public class SftpUtil {

	private String user;
	private String password;
	private String ip;
	private int port;
	private Channel channel = null;
	private Session session = null;
	private ChannelExec channelExec = null;

	public SftpUtil(String user, String password, String ip, int port) {
		this.user = user;
		this.password = password;
		this.ip = ip;
		this.port = port;
	}

	public String runCommand(String runCmd) {
		String result = "";

		try {
			channelExec = openChannel();

			if (channelExec == null) {	// 채널 오픈
				throw new JSchException();
			}

			channelExec.setCommand(runCmd);

			//콜백을 받을 준비.
	        StringBuilder out = new StringBuilder();
	        InputStream in = channel.getInputStream();
	        ((ChannelExec) channel).setErrStream(System.err);

			channelExec.connect();	//실행

			result = callBack(in, out);	// 결과 반환

		} catch (Exception e) {
			log.error("명령어 실행 실패  - {}", runCmd, e);
			result = e.getMessage();
		} finally {
			disconnect();
		}

		return result;
	}

	private ChannelExec openChannel() {
		JSch jsch = new JSch();
		Properties prop = new Properties();			// 세션 관련 정보 설정위해 선언
		ChannelExec result = null;

		try {
			prop.put("StrictHostKeyChecking", "no");	// 호스트 정보를 검사하지 않는다.

			session = jsch.getSession(user, ip, port);  // 세션 객체 생성
			session.setConfig(prop);
			session.setPassword(password);
			session.connect();	// 연결

			channel = session.openChannel("exec");		// sftp 채널을 연다.
			result = (ChannelExec) channel;	// 채널을 SSH용 채널 객체로 캐스팅한다
			//result.setPty(true);
		} catch (JSchException e) {
			log.error("채널이 정상적으로 열리지 않았습니다. - ip : {}, port : {}, user : {}" ,ip, port, user, e);
			result = null;
		}

		return result;
	}

	private String callBack(InputStream in, StringBuilder out) throws IOException {
		byte[] tmp = new byte[1024];

		String result = "";

		while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                out.append(new String(tmp, 0, i));
                if (i < 0) break;
            }
            if (channel.isClosed()) {
                result = out.toString();
                break;
            }
        }
		return result;
	}

	private void disconnect() {
		if (channelExec != null) {
			channelExec.disconnect();
			channelExec = null;
		}

		if (channel != null) {
	        channel.disconnect();
	        channel = null;
	    }

	    if (session != null) {
	        session.disconnect();
	        session = null;
	    }
	}
}
