/**

**/

import java.util.Collection;

import embedded.mas.bridges.jacamo.JSONWatcherDevice;
import embedded.mas.bridges.jacamo.IPhysicalInterface;
import embedded.mas.bridges.jacamo.DefaultDevice;
import embedded.mas.bridges.jacamo.LiteralDevice;
import embedded.mas.bridges.ros.DefaultRos4EmbeddedMas;
import jason.asSyntax.Atom;
import jason.asSyntax.Literal;

import embedded.mas.bridges.ros.RosMaster;
import embedded.mas.bridges.ros.DefaultRos4EmbeddedMas;
import embedded.mas.bridges.ros.ServiceParameters;
import embedded.mas.bridges.ros.ServiceParam;

import jason.asSyntax.Atom;
import jason.asSyntax.Term;
import jason.asSyntax.ListTerm;
import static jason.asSyntax.ASSyntax.parseList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;



public class MyRosMaster extends RosMaster {

	public MyRosMaster(Atom id, DefaultRos4EmbeddedMas microcontroller) {
		super(id, microcontroller);
	}

        /* Translate actions into ros topic publications 
           args: 0. Topic name
                 1. Topic type
                 2. Topic value
           obs: args are Strings. The action arguments are send to the ros as strings.
                Type conversions are handled in the "microcontroller" (DefaultRos4EmbeddedMas)       
        */
	@Override
	public boolean execEmbeddedAction(String actionName, Object[] args) { //Poderia deixar so o Critico e os demais no yaml, critico fica por causa do belief que tem que ser adicionado
	
		
		if(actionName.equals("drop")){		   
	      ((DefaultRos4EmbeddedMas) microcontroller).rosWrite("/rescue_world/drop_buoy","geometry_msgs/Pose","{\"position\": {\"x\": "+args[0]+", \"y\": "+args[1]+", \"z\": "+args[2]+"},\"orientation\": {\"x\": 0.0, \"y\": 0.0, \"z\": 0.0, \"w\": 99.0}}");
		   return true;
	   }
	   if(actionName.equals("adf")){	//adicionar belief failure -+status("failure");	   
	      ((DefaultRos4EmbeddedMas) microcontroller).rosWrite("/agent_detected_failure_uav1","std_msgs/String",(String)args[0]);
		   return true;
	   }

		if(actionName.equals("goto")){ 
			ServiceParameters p = new ServiceParameters(); //p is the set of parameters of the requested service		  
		    p.addParameter("goal", new Float[]{Float.parseFloat(args[1].toString()), Float.parseFloat(args[2].toString()), Float.parseFloat(args[3].toString()), Float.parseFloat(args[4].toString())} ); //adding a new parameter which is an array of double		   
			serviceRequest("/uav"+args[0]+"/control_manager/goto", p); 
			return true;

		}

		if(actionName.equals("land")){ //handling the action "move_turtle"

			ServiceParameters p = new ServiceParameters(); 
			serviceRequest("/uav"+args[0]+"/uav_manager/land", p); //send the service request		   
			return true;

		}
		return false;

	}
	
}
