import embedded.mas.bridges.ros.IRosInterface;
import embedded.mas.bridges.ros.RosMaster;
import embedded.mas.bridges.ros.DefaultRos4EmbeddedMas;

import jason.asSyntax.Atom;
import jason.asSyntax.Literal;
import jason.asSyntax.Term;
import jason.asSemantics.Unifier;

import embedded.mas.bridges.ros.ServiceParameters;
import embedded.mas.bridges.ros.ServiceParam;

public class MyRosMaster extends RosMaster{

    public MyRosMaster(Atom id, IRosInterface microcontroller) {
        super(id, microcontroller);
    }
    

    @Override
	public boolean execEmbeddedAction(String actionName, Object[] args, Unifier un) {		
		//execute the actions configured in the yaml file
        super.execEmbeddedAction(actionName, args, un);  // <- do not delete this line

		//Execute a customized actions 
          
		// The action "update_value" is realized through the writing in 2 topics */
		if(actionName.equals("teste2")){		   
		   ((DefaultRos4EmbeddedMas) this.getMicrocontroller()).rosWrite("/teste","std_msgs/String",(String)args[0]);
		}
		
		if(actionName.equals("goto")){ 
			ServiceParameters p = new ServiceParameters(); //p is the set of parameters of the requested service		  
		    p.addParameter("goal", new Float[]{Float.parseFloat(args[0].toString()), Float.parseFloat(args[1].toString()), Float.parseFloat(args[2].toString()), Float.parseFloat(args[3].toString())} ); //adding a new parameter which is an array of double		   
			serviceRequest("/uav1/control_manager/goto", p); 
			return true;

		}

		

		return true;
	}

}