package utils;

public enum CommandStatus {
	
	    pending (1),
	    in_processing (2),
	    completed (3),
	    error (4);
		
		private int code;
		
		private CommandStatus(int code) {
			this.code = code;
		}
		
		public int getStatusCode() {
			return this.code;
		}

}
