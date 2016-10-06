package hello;



import static spark.Spark.get;
import static spark.Spark.post;


import java.io.UnsupportedEncodingException;
import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import spark.Request;
import spark.Response;
import spark.Route;


public class REST{
	
	private Model model;
	
	
	public REST(Model model){
		this.model = model;
	}
	
	
	public void getLogin(){
		
		get("/login/:username/:password", new Route() {
			
            public Object handle(final Request request, final Response response){
	        	
	        	 
	        	 
	        	response.header("Access-Control-Allow-Origin", "*");
	        	 
	            
	            
	            try {
	            	User user = model.login(request.params(":username"), request.params(":password"));
	            	
	            	if(user != null){
	            		
	            		JSONArray jsonArray = new JSONArray();
		         	    JSONObject jsonObj = new JSONObject();
		         	    
		         	    
		        		jsonObj.put("userName", user.getUserName());
		        		jsonObj.put("nivelAcesso", user.getNivelAcesso());
		        		
		        		
		             	jsonArray.put(jsonObj);
		             	
		             	return jsonArray;
	            		
	            	} else {
	            		
	            		
	            		
	            	}
	            	
	            	
	             	
	        		} catch (JSONException e) {
	        				
	        			//e.printStackTrace();

	        		}
	         	    	
	
	            JSONArray jsonArray = new JSONArray();
         	    JSONObject jsonObj = new JSONObject();

        		jsonObj.put("userName", "");
        		jsonObj.put("nivelAcesso", "");
        		
        		jsonArray.put(jsonObj);
             	
             	return jsonArray;
	            
	     	     
	         }
	         
	      });
			

	}
	
	
	public void cadastrarNovoUsuario(){
		post("/cadastrarUsuario", new Route(){
			public Object handle(final Request request, final Response response){
				response.header("Access-Control-Allow-Origin", "*");
				
				Gson gson = new Gson();
				String json_string = request.body();
				User user = gson.fromJson(json_string, User.class);
				
				try{
					boolean status = model.addUser(user);
					if(status){
						JSONArray jsonArray = new JSONArray();
	 	         	    JSONObject jsonObject = new JSONObject();
	 	         	    
	 	         	    jsonObject.put("status", 1);
	 	         	    jsonObject.put("body", json_string);

		             	jsonArray.put(jsonObject);

		             	return jsonArray;
					}
				}catch (JSONException e) {
					e.printStackTrace();
				}
				
				JSONArray jsonArray = new JSONArray();
         	    JSONObject jsonObject = new JSONObject();
         	   	
         	    jsonObject.put("status", 0);
         	    	jsonObject.put("body", json_string);
        		
         	    jsonArray.put(jsonObject);
             	
             	return jsonArray;
			}
		});
	}
	
	public void criarChamado(){
		post("/abrirChamado", new Route(){
			
			public Object handle(final Request request, final Response response){
				response.header("Access-Control-Allow-Origin", "*");
				
				/*
				 GSON � uma biblioteca do Google utilizada, entre outras coisas, na convers�o de 
				 objetos Java em representa��o JSON. Tamb�m pode ser utilizada para converter uma 
				 cadeia JSON para um objetos Java equivalentes. A estrutura b�sica JSON para 
				 representar um simples objeto � um conjunto desordenado de pares nome/valor. Um
				 objeto come�a com { (chave esquerda) e termina com } (chave direita). Cada nome � 
				 seguido por : (dois pontos) e os pares nome/valor s�o separados por , (v�rgula). A 
				 Figura 1 mostra a estrutura de um objeto em JSON.
				 
				  Informacoes obj = new Informacoes();
				  converte objetos Java para JSON e retorna JSON como String
				  String json_string = gson.toJson(obj);
				  
				  //Converte String JSON para objeto Java
				    Informacoes obj = gson.fromJson(json_string, Informacoes.class);
				*/
				Gson gson = new Gson();
				String json_string = request.body();
				Chamado novoChamado = gson.fromJson(json_string, Chamado.class);
				
				try{
					boolean status = model.addChamado(novoChamado);
					if(status){
						JSONArray jsonArray = new JSONArray();
	 	         	    JSONObject jsonObject = new JSONObject();
	 	         	    
	 	         	    jsonObject.put("status", 1);
	 	         	    jsonObject.put("body", json_string);

		             	jsonArray.put(jsonObject);

		             	return jsonArray;
					}
				}catch (JSONException e) {
					e.printStackTrace();
				}
				
				JSONArray jsonArray = new JSONArray();
         	    JSONObject jsonObject = new JSONObject();
         	   	
         	   jsonObject.put("status", 0);
        		
        		
         	   jsonArray.put(jsonObject);
             	
             	return jsonArray;
			}
			
		});
	}
	
	
	public void listarChamados(){
		get("/listarChamados", new Route(){
			
			public Object handle(final Request request, final Response response){
				response.header("Access-Control-Allow-Origin", "*");
				
				JSONArray jsonArray = new JSONArray();
				
				try{
					List<Chamado> chamados = model.getAllChamados();
					
					for(Chamado chamado : chamados){
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("numeroChamado", chamado.getNumeroChamado());
						jsonObject.put("nomeUsuarioChamado", chamado.getNomeUsuarioChamado());
						jsonObject.put("descricaoChamado", chamado.getDescricaoChamado());
						jsonObject.put("dataChamado", chamado.getDataChamado());
						jsonObject.put("situacaoChamado", chamado.getSituacaoChamado());
						jsonArray.put(jsonObject);
					}
					return jsonArray;
					
				}catch(JSONException e){
					e.printStackTrace();
				}
				
				return null;
			}
		});
	}

	
	public void listarChamadosDoUsuario(){
		get("/listarChamadosDoUsuario/:userName", new Route(){
			
			public Object handle(final Request request, final Response response){
				response.header("Access-Control-Allow-Origin", "*");
				
				String userName = request.params(":userName");
				
				JSONArray jsonArray = new JSONArray();
				
				try{
					List<Chamado> chamados = model.getAllChamados();
					
					for(Chamado chamado : chamados){
						JSONObject jsonObject = new JSONObject();
						if(chamado.getNomeUsuarioChamado().equals(userName)){
							jsonObject.put("numeroChamado", chamado.getNumeroChamado());
							jsonObject.put("nomeUsuarioChamado", chamado.getNomeUsuarioChamado());
							jsonObject.put("descricaoChamado", chamado.getDescricaoChamado());
							jsonObject.put("dataChamado", chamado.getDataChamado());
							jsonObject.put("situacaoChamado", chamado.getSituacaoChamado());
							jsonArray.put(jsonObject);
						}
					}
					return jsonArray;
					
				}catch(JSONException e){
					e.printStackTrace();
				}
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("userName", userName);
				jsonArray.put(jsonObject);
				return jsonArray;
			}
		});
	}
	
	public void excluirChamado(){
		post("/excluirChamado", new Route(){
			public Object handle(final Request request, final Response response){
				response.header("Access-Control-Allow-Origin", "*");
				
				JSONObject jsonObject = new JSONObject(request.body());
				int numeroChamado = Integer.parseInt( jsonObject.getString("numeroChamado")); //JSONObject.getString(String key): String 
				
				try{
					model.excluirChamado(numeroChamado);
					JSONArray jsonArray = new JSONArray();
					JSONObject jsonObject2 = new JSONObject();
					
					jsonObject2.put("status", 1);
					jsonArray.put(jsonObject2);
					return jsonArray;
				}catch(JSONException e){
					e.printStackTrace();
				}
				
				JSONArray jsonArray2 = new JSONArray();
         	    JSONObject jsonObject3 = new JSONObject();
         	   	
         	   jsonObject3.put("status", 0);
        		
        		
         	  jsonArray2.put(jsonObject3);
             	
             	return jsonArray2;
			}
		});
	}
	
	
}



	