package utils;

public enum ArtifactStatus {
	
    ARTIFACT_CREATED ("Artefatto Creato"),
    SERVICE_CONNECTED ("Collegato al servizio"),
    SERVICE_UNREACHABLE ("Servizio non raggiungibile"),
    SERVICE_ERROR ("Errore nel servizio");
	
	private String status;
	
	private ArtifactStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}

}
