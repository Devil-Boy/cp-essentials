package pgDev.bukkit.CommandPointsEssentials;

public class CTPRequest {
	String requesterName;
	boolean toRequester;
	
	public CTPRequest(String requesterName, boolean toRequester) {
		this.requesterName = requesterName;
		this.toRequester = toRequester;
	}
}
