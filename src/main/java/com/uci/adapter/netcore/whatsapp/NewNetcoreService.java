package com.uci.adapter.netcore.whatsapp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.web.reactive.function.client.WebClient;

import com.azure.core.util.BinaryData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.uci.adapter.netcore.whatsapp.outbound.ManageUserRequestMessage;
import com.uci.adapter.netcore.whatsapp.outbound.ManageUserResponse;
import com.uci.adapter.netcore.whatsapp.outbound.OutboundMessage;
import com.uci.adapter.netcore.whatsapp.outbound.OutboundOptInOutMessage;
import com.uci.adapter.netcore.whatsapp.outbound.SendMessageResponse;
import com.uci.adapter.netcore.whatsapp.outbound.SingleMessage;
import com.uci.adapter.netcore.whatsapp.outbound.nic.Address;
import com.uci.adapter.netcore.whatsapp.outbound.nic.NewOutboundMessage;
import com.uci.adapter.netcore.whatsapp.outbound.nic.NewSendMessageResponse;
import com.uci.adapter.netcore.whatsapp.outbound.nic.Sms;
import com.uci.adapter.netcore.whatsapp.outbound.nic.User;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import reactor.core.publisher.Mono;

public class NewNetcoreService {

    private final WebClient webClient;
    private OkHttpClient client;
    private MediaType mediaType;
    private String baseURL;
    private NWCredentials credentials;

    private static NewNetcoreService newNetcoreService = null;

    public NewNetcoreService(NWCredentials credentials) {
	System.out.println("########## NewNetcoreService Adapter NewNetcoreService credentials.getToken() "+credentials.getToken());
        this.client = new OkHttpClient().newBuilder().build();
        this.mediaType = MediaType.parse("application/json");
        String url = System.getenv("NETCORE_WHATSAPP_URI");
        url = url != null && !url.isEmpty() ? url : "https://waapi.pepipost.com/api/v2/";
        this.baseURL = url;
        this.credentials = credentials;
        webClient = WebClient.builder()
                .baseUrl(url)
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", credentials.getToken())
                .build();
    }

    public static NewNetcoreService getInstance(NWCredentials credentials) {
        if (newNetcoreService == null) {
            return new NewNetcoreService(credentials);
        } else {
            return newNetcoreService;
        }
    }

    public ManageUserResponse manageUser(ManageUserRequestMessage message) {
        ObjectMapper mapper = new ObjectMapper();
        RequestBody body = null;
	System.out.println("########## NewNetcoreService Adapter manageUser credentials.getToken() "+credentials.getToken());
        try {
            body = RequestBody.create(mediaType, mapper.writeValueAsString(message));
            Request request = new Request.Builder()
                    .url(baseURL + "consent/manage")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", credentials.getToken())
                    .build();
            Response response = client.newCall(request).execute();
            String json = response.body().toString();
            return mapper.readValue(json, ManageUserResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public SendMessageResponse sendText(OutboundMessage message) {
        ObjectMapper mapper = new ObjectMapper();
        RequestBody body = null;
	System.out.println("########## NewNetcoreService Adapter sendText credentials.getToken() "+credentials.getToken());
        try {
            body = RequestBody.create(mediaType, mapper.writeValueAsString(message));
            Request request = new Request.Builder()
                    .url(baseURL + "message/")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", credentials.getToken())
                    .build();
            Response response = client.newCall(request).execute();
            String json = response.body().string();
            return mapper.readValue(json, SendMessageResponse.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public Mono<NewSendMessageResponse> sendOutboundMessage(OutboundMessage outboundMessage) {
    	System.out.println("sendOutboundMessage in Adapter");
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	
    	NewOutboundMessage newOutboundMessage = new NewOutboundMessage();
    	
    	try {
    		// Openforge#146367 whatsApp vendor chnage
    		// convert existing netcore outbound message to new NIC outbound
    		newOutboundMessage = convertOutboundRequestToNewVendorRequest(outboundMessage, newOutboundMessage);
    		
    		Gson gson = new Gson();
    		String json = gson.toJson(newOutboundMessage);
			String jsonNetCore = ow.writeValueAsString(outboundMessage);
			
			System.out.println("jsonNetCore : "+jsonNetCore);
			System.out.println("json file for NIC : "+json);
		} catch (Exception e) {
			System.out.println("json not converted:"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        /*return webClient.post()
                .uri("/message/")
                .body(Mono.just(newOutboundMessage), NewOutboundMessage.class)
                .retrieve()
                .bodyToMono(SendMessageResponse.class)
                .map(new Function<SendMessageResponse, SendMessageResponse>() {
                    @Override
                    public SendMessageResponse apply(SendMessageResponse sendMessageResponse) {
                        if (sendMessageResponse != null) {
                            System.out.println("MESSAGE RESPONSE " + sendMessageResponse.getMessage());
                            System.out.println("STATUS RESPONSE " + sendMessageResponse.getStatus());
                            System.out.println("DATA RESPONSE " + sendMessageResponse.getData());
                            return sendMessageResponse;
                        } else {
                            return null;
                        }
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        System.out.println("ERROR IS " + throwable.getLocalizedMessage());
                    }
                });*/
    	System.out.println("Sending POST request to Vendor API");
    	return webClient.post()
                .body(Mono.just(newOutboundMessage), NewOutboundMessage.class)
                .acceptCharset(Charset.defaultCharset())
                .retrieve()
                .bodyToMono(NewSendMessageResponse.class)
                .map(new Function<NewSendMessageResponse, NewSendMessageResponse>() {
                    @Override
                    public NewSendMessageResponse apply(NewSendMessageResponse sendMessageResponse) {
                    	System.out.println("sendMessageResponse "+sendMessageResponse);
                        if (sendMessageResponse != null) {
                        	System.out.println(sendMessageResponse.getMESSAGEACK().getGuid().getGuid());
                        	System.out.println(sendMessageResponse.getMESSAGEACK().getGuid().getId());
                        	System.out.println(sendMessageResponse.getMESSAGEACK().getGuid().getSubmitDate());
                            //System.out.println("MESSAGE RESPONSE " + sendMessageResponse.getMessage());
                            //System.out.println("STATUS RESPONSE " + sendMessageResponse.getStatus());
                            //System.out.println("DATA RESPONSE " + sendMessageResponse.getData());
                            return sendMessageResponse;
                        } else {
                            return null;
                        }
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        System.out.println("ERROR IS " + throwable.getLocalizedMessage());
                    }
                });
    }

	private NewOutboundMessage convertOutboundRequestToNewVendorRequest(OutboundMessage outboundMessage,
			NewOutboundMessage newOutboundMessage) {
		List<Sms> smsList = new ArrayList<>();
		
		for(SingleMessage singleMessage : outboundMessage.getMessage()) {
			Sms sms = new Sms();
		    sms.setAddress(new ArrayList<>());
      
		    Address address = new Address();
		    address.setFrom("919310200148"); // Need to check with New Vendor (TODO number from property file)
		    address.setTo(singleMessage.getTo());
		    address.setSeq("");
		    address.setTag("");
		    sms.getAddress().add(address);
      
		    sms.setMessageType(singleMessage.getMessageType());
		    sms.setText(singleMessage.getText()[0].getContent());
      
		    sms.setUdh("0");
		    sms.setCoding("");
		    sms.setTemplateInfo("");
		    sms.setId("");
		    sms.setMessageType("1");
		    sms.setProperty("0");
		    smsList.add(sms);
		}
		newOutboundMessage.setSms(smsList);
		newOutboundMessage.setVersion("1.2");
		User user = new User();
		user.setChannelType("4");
		user.setTimestamp("");
		newOutboundMessage.setUser(user);
		return newOutboundMessage;
	}
    
    public Mono<SendMessageResponse> sendOutboundOptInOutMessage(OutboundOptInOutMessage outboundMessage) {
    	System.out.println("sendOutboundOptInOutMessage");
    	ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    	try {
			String json = ow.writeValueAsString(outboundMessage);
			System.out.println("json:"+json);
		} catch (JsonProcessingException e) {
			System.out.println("json not converted:"+e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return webClient.post()
                .uri("/consent/manage")
                .body(Mono.just(outboundMessage), OutboundOptInOutMessage.class)
                .retrieve()
                .bodyToMono(SendMessageResponse.class)
                .map(new Function<SendMessageResponse, SendMessageResponse>() {
                    @Override
                    public SendMessageResponse apply(SendMessageResponse sendMessageResponse) {
                        if (sendMessageResponse != null) {
                            System.out.println("sendOutboundOptInOutMessage MESSAGE RESPONSE " + sendMessageResponse.getMessage());
                            System.out.println("sendOutboundOptInOutMessage STATUS RESPONSE " + sendMessageResponse.getStatus());
                            System.out.println("sendOutboundOptInOutMessage DATA RESPONSE " + sendMessageResponse.getData());
                            return sendMessageResponse;
                        } else {
                        	System.out.println("sendOutboundOptInOutMessage response is null.");
                            return null;
                        }
                    }
                }).doOnError(new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        System.out.println("ERROR IS " + throwable.getLocalizedMessage());
                    }
                });
    }
    
    /**
     * Get Media File from netcore by id
     * @param id
     * @return
     */
    public InputStream getMediaFile(String id) {
    	ObjectMapper mapper = new ObjectMapper();
	System.out.println("########## NewNetcoreService Adapter getMediaFile credentials.getToken() "+credentials.getToken());
        try {
            Request request = new Request.Builder()
                    .url(baseURL + "media/"+id)
                    .get()
                    .addHeader("Authorization", "Bearer " + credentials.getToken())
                    .build();
            
            Response response = client.newCall(request).execute();

            ResponseBody body = response.body();
            
            InputStream in = body.byteStream();
            
            if(body.contentLength() <= 0) {
            	System.out.println("Media file content length is 0");
            	return null;
            }
            
            return in;
        } catch (Exception e ) {
        	System.out.println("Exception in netcore getMediaFile: "+e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}
