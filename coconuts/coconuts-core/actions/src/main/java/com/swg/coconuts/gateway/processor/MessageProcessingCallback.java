package com.swg.coconuts.gateway.processor;



import com.swg.coconuts.gateway.IIncomingMessage;
import com.swg.coconuts.gateway.processor.MessageProcessingException.MessagePostProcessingException;
import com.swg.coconuts.gateway.processor.MessageProcessingException.MessagePreProcessingException;

/**
 * Kontrak untuk callback pemrosesan pesan masuk.
 * Contoh yang bisa dilakukan di sini adalah, validasi nomor pengirim
 * secara global (misalnya, apakah system bisa memproses sms dari unknown sender atau tidak)
 * walaupun validasi nomor pengirim bisa dilakukan per-aksi,  jika disini gagal (atau invalid) 
 * dalam proses validasi, maka pemrosesan message bersangkutan dihentikan.
 * 
 * @author zakyalvan
 *
 */
public interface MessageProcessingCallback {
	public interface HasMessageProcessingCallback {
		void setProcessorCallback(MessageProcessingCallback callback);
	}
	
	/**
	 * Default implementasi untuk kontrak processsing callback ini.
	 * Setiap callback method tidak melakukan apapun.
	 * 
	 * @author zakyalvan
	 */
	public static class Default implements MessageProcessingCallback {
		@Override
		public void onPreProcess(IIncomingMessage incomingMessage) throws MessagePreProcessingException {}
		@Override
		public void onPostProcess(IIncomingMessage incomingMessage) throws MessagePostProcessingException {}
		
		@Override
		public void onProcessFailure(ProcessFailureInfo processFailureInfo) {}
	};
	
	public static final MessageProcessingCallback DUMB = new MessageProcessingCallback.Default();
	
	void onPreProcess(IIncomingMessage incomingMessage) throws MessagePreProcessingException;
	void onPostProcess(IIncomingMessage incomingMessage) throws MessagePostProcessingException;
	
	void onProcessFailure(ProcessFailureInfo processFailureInfo);
}