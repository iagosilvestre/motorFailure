

import java.util.ArrayList;
import java.util.Collection;

import embedded.mas.bridges.jacamo.JSONWatcherDevice;
import embedded.mas.bridges.jacamo.DefaultDevice;
import embedded.mas.bridges.jacamo.JSONDevice;
import embedded.mas.bridges.jacamo.IPhysicalInterface;
import jason.asSyntax.Atom;
import jason.asSyntax.Literal;
import embedded.mas.exception.PerceivingException;

import embedded.mas.bridges.javard.Arduino4EmbeddedMas;
import embedded.mas.bridges.jacamo.IPhysicalInterface;
/**
 * This device enables two actions: lightA and lightB, that are meant to turn on different lights in the physical device.
 * 
 * The actions lightA and lightB send to the microcontroller the messages "light_a" and "light_b". The microcontroller is expected to properly handle them,
 * turning on the light. A or B accordingly.
 * 
 * The names of the actions (lightA and lightB) are different from the messages sent to microcontroller ("light_a" and "light_b") to illustrate the 
 * decoupling between the agent level (the actions) and the physical level (the handling of messages). Though actions names and messages could be equal.
 *
 */

public class MyDemoDevice extends DefaultDevice {

	public MyDemoDevice(Atom id, IPhysicalInterface microcontroller) {
		super(id, microcontroller);
	}

	String s0="0";  
   	String s1="1";  
	
	@Override
	public Collection<Literal> getPercepts() {
		ArrayList<Literal> percepts = new ArrayList<Literal>();
		percepts.add(Literal.parseLiteral("teste_percept"));
        	String s = microcontroller.read(); //read from serial until getting a linebreak (\n)
		if (s==s0){
			percepts.add(Literal.parseLiteral("teste 0"));
		}
		else if (s==s1){
			percepts.add(Literal.parseLiteral("teste 1"));
		}
		return percepts;
	}
	
	@Override
	public boolean execEmbeddedAction(String arg0, Object[] arg1) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public IPhysicalInterface getMicrocontroller() {
		return (IPhysicalInterface) this.microcontroller;
	}


	// # atuacoes

}
