package roadmap.agents.packets;

import java.io.Serializable;
import java.util.ArrayList;

public class WorldUpdatePacket implements Serializable {

	private static final long serialVersionUID = 1L;
	ArrayList<UpdateInfo> info;
	
	public WorldUpdatePacket() {
		info = new ArrayList<UpdateInfo>();
	}
	
	public ArrayList<UpdateInfo> getInfo() {
		return info;
	}
	
	public void addUpdateInfo(UpdateInfo uInfo) {
		info.add(uInfo);
	}
	
}
