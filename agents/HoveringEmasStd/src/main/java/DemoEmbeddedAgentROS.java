import embedded.mas.bridges.jacamo.EmbeddedAgent;
import embedded.mas.bridges.jacamo.JSONDevice;
import embedded.mas.bridges.jacamo.DefaultDevice;
import embedded.mas.bridges.ros.DefaultRos4EmbeddedMas;

import jason.asSyntax.Atom;

import java.util.ArrayList;

public class DemoEmbeddedAgentROS extends EmbeddedAgent {
	
	private ArrayList<String> nodes = new ArrayList<String>();
	private ArrayList<String> topics = new ArrayList<String>();

	@Override
	public void initAg() {
		super.initAg();
	}

	@Override
	protected void setupDevices() {
		
               
		/* Setting topic-belief conversion. The first parameter is the topic name; the second is the topic type (can be cheched using "rostopic info" command) */
		//addTopic("uav1/mavros/state", "mavros_msgs/State");

        addTopic("failure_uav1", "std_msgs/Int8");
               
		/* roscore1 is a connection with a ros master. Instantiate new DefaultRos4EmbeddedMas connect the agent with more ros masters*/
		DefaultRos4EmbeddedMas roscore1 = new DefaultRos4EmbeddedMas("ws://localhost:9090",nodes, topics);		
   	    MyRosMaster rosMaster = new MyRosMaster(new Atom("roscore1"), roscore1);
		this.addSensor(rosMaster);
		
	}



	private void addTopic(String topicName, String topicType){
	   nodes.add(topicName); 
	   topics.add(topicType);
	}
}	