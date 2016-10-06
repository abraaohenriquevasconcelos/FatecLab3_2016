package hello;

import java.util.LinkedList;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.query.Query;

public class Model{
	/*
	  To access a db4o database file or create a new one, call Db4oEmbedded.openFile() and
	   provide Db4oEmbedded.newConfiguration() as a configuration template abd path to your 
	   database, to obtain an ObjectContainer instance. ObjectContainer represents "The 
	   Database"
	   
	   When using Query By Example (QBE) you provide db4o with a template object. db4o will
	   return all of the objects  which match all field values.  This is done via 
	   reflecting all of the fields and building a query expression:
		Pilot proto = new Pilot("Michael Dumum", 0);
		ObjectSet result = pilots.queryByExample(proto);
		listResult(result);

		The SODA Query API is db4o's low level querying, allowing direct access to nodes of
		query graphs. Since SOda uses strings to identify fields, it is neither perfectly
		typesafe nor compile-time checked and it also is quite verbose to write.
		Let's see how our familiar QBE queries are expressed with SODA. A new Query object is created through the query() method of the ObjectContainer and we can add Constraint instances to it. 
		Query query = pilots.query();
		query.constrain(Pilot.Class);
		ObjectSet result = query.execute();
	 */
	ObjectContainer users = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/users.db4o");
	ObjectContainer chamados = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "bd/chamados.db4o");
	
	
	public boolean addUser(User user){
		
		users.store(user);
		users.commit();
		
		return true;
	}
	
	public boolean addChamado(Chamado chamado){
		Query query = chamados.query(); //query(): Query - OnjectContainer
		query.constrain(Chamado.class); //constrain(Object arg0): Constraint - Query
		ObjectSet<Chamado> allChamados = query.execute();
		//inteiro para string -> Integer.toString(int) , string para inteiro -> Integer.parseInt(string)
		chamado.setNumeroChamado(Integer.toString(allChamados.size()));
		chamados.store(chamado);
		chamados.commit();
		return true;
	}
	
	public User login(String email, String senha){
		Query query = users.query();
		query.constrain(User.class);
	    ObjectSet<User> allUsers = query.execute();
	    
	    for(User user: allUsers){
	    	if(user.getUserName().equals(email) && user.getPassword().equals(senha)) return user;
	    }
	    
	    return null;
	}
	
	public List<Chamado> getAllChamados(){
		Query query = chamados.query();
		query.descend("numeroChamado").orderAscending();
		List<Chamado> chamados = query.execute();
		return chamados;
	}
	
	
	
	
	public void excluirChamado(int numeroChamado){
		Query query = chamados.query();
		query.constrain(Chamado.class);
		List<Chamado> todosChamados = query.execute();
		
		for(Chamado chamado : todosChamados){
			int numChamado = Integer.parseInt(chamado.getNumeroChamado());
			if(numChamado ==  numeroChamado){
				chamados.delete(chamado);
				chamados.commit();
			}
		}
	}
	
	
	
}


