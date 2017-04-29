package pitayaa.nail.plivo.test;

/* * Copyright (c) 2011-2016 Nexmo Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
*/ 


/**
 * SendTextMessage.java<br><br>
 *
 * An example of using the nexmo sms api to submit a simple text message ...<br><br>
 *
 * Created on 5 January 2011, 17:34
 *
 * @author  Paul Cook
 * @version 1.0
 */
public class NexmoSms {

    public static final String API_KEY = "bd49d0df";
    public static final String API_SECRET = "f1e525b0f9ecae5f";

    public static final String SMS_FROM = "12345";
    public static final String SMS_TO = "841693882705";
    public static final String SMS_TEXT = "Test sms from nexmo";
    
	/*public static String getSMSKey(){
		//String value = "106-105-181-110-34-200-113-199-172-141-160-58-249-191-28-58";
		String value2 = "136-153-118-253-84-127-58-229-172-141-160-58-249-191-28-58";
		//String keyToEncrypt = "9debAcc7";
		String apiKeyEncrypt = EncryptionUtils.encrypt(API_KEY, EncryptionUtils.getKey());
		String apiSecretEncrypt = EncryptionUtils.encrypt(API_SECRET, EncryptionUtils.getKey());
		//String valueAfterEncrypt = EncryptionUtils.decrypt(value2, EncryptionUtils.getKey());
		if(StringUtil.isNullOrEmpty(apiKeyEncrypt)){
			return "";
		} else{
			return EncryptionUtils.decrypt(apiKeyEncrypt,EncryptionUtils.getKey());
		}
	}*/

    /*public static void main(String[] args) {

        // Create a client for submitting to Nexmo
    	
    	String encrypt = getSMSKey();
    	System.out.println("encrypt = " + encrypt);
        NexmoSmsClient client = null;
        try {
            client = new NexmoSmsClient(API_KEY, encrypt);
        } catch (Exception e) {
            System.err.println("Failed to instantiate a Nexmo Client");
            e.printStackTrace();
            throw new RuntimeException("Failed to instantiate a Nexmo Client");
        }

        // Create a Text SMS Message request object ...

        TextMessage message = new TextMessage(SMS_FROM, SMS_TO, SMS_TEXT);

        // Use the Nexmo client to submit the Text Message ...

        SmsSubmissionResult[] results = null;
        try {
            results = client.submitMessage(message);
        } catch (Exception e) {
            System.err.println("Failed to communicate with the Nexmo Client");
            e.printStackTrace();
            throw new RuntimeException("Failed to communicate with the Nexmo Client");
        }

        // Evaluate the results of the submission attempt ...
        System.out.println("... Message submitted in [ " + results.length + " ] parts");
        for (int i=0;i<results.length;i++) {
            System.out.println("--------- part [ " + (i + 1) + " ] ------------");
            System.out.println("Status [ " + results[i].getStatus() + " ] ...");
            if (results[i].getStatus() == SmsSubmissionResult.STATUS_OK)
                System.out.println("SUCCESS");
            else if (results[i].getTemporaryError())
                System.out.println("TEMPORARY FAILURE - PLEASE RETRY");
            else
                System.out.println("SUBMISSION FAILED!");
            System.out.println("Message-Id [ " + results[i].getMessageId() + " ] ...");
            System.out.println("Error-Text [ " + results[i].getErrorText() + " ] ...");

            if (results[i].getMessagePrice() != null)
                System.out.println("Message-Price [ " + results[i].getMessagePrice() + " ] ...");
            if (results[i].getRemainingBalance() != null)
                System.out.println("Remaining-Balance [ " + results[i].getRemainingBalance() + " ] ...");
        }
    }*/

}
