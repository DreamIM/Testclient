package de.mrpixeldream.dreamim.testclient;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.jasypt.util.text.StrongTextEncryptor;

import de.mrpixeldream.dreamcode.im.server.io.MessageWrapper;

public class Testclient
{
	public static void main(String[] args)
	{
		try
		{
			Socket server = new Socket("127.0.0.1", 22558);
			ObjectOutputStream objOut = new ObjectOutputStream(server.getOutputStream());
			ObjectInputStream objIn = new ObjectInputStream(server.getInputStream());
			StrongTextEncryptor enc = new StrongTextEncryptor();
			enc.setPassword("Gummiball");
			String testBroadcast = "BROADCAST test";
			MessageWrapper wrapperBroadcast = new MessageWrapper(enc.encrypt(testBroadcast));
			MessageWrapper wrapperLogin = new MessageWrapper(enc.encrypt("LOGIN MrPixelDream c788d4d809a916f6869f333dca309e3acf3b2b1a"));
			MessageWrapper wrapperLogout = new MessageWrapper(enc.encrypt("LOGOUT"));
			
			objOut.writeObject(wrapperLogin);
			objOut.flush();
			MessageWrapper response = (MessageWrapper) objIn.readObject();
			System.out.println(response);
			objOut.writeObject(wrapperBroadcast);
			objOut.flush();
			response = (MessageWrapper) objIn.readObject();
			System.out.println(response);
			objOut.writeObject(wrapperLogout);
			objOut.flush();
			
			objOut.close();
			objIn.close();
			
			server.close();
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
