package com.swg.coconuts.action.format;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.swg.coconuts.action.param.MapParameter;
import com.swg.coconuts.action.param.NumberParameter;
import com.swg.coconuts.action.param.Parameter;
import com.swg.coconuts.action.param.StringParameter;
import com.swg.coconuts.action.sampler.Sampler;
import com.swg.coconuts.action.sampler.SamplerFactory;
import com.swg.coconuts.action.sampler.SamplerFactory.SamplerType;

/**
 * Implementasi sederhana {@link Parser}. Kelas object yang digunakan untuk
 * memparsing isi pesan masuk sebelum action dieksekusi. Proses parsing ini dihint oleh
 * object {@link Format}, yaitu format valid yang dapat diterima oleh action.
 * 
 * @author zakyalvan
 */
public class SimpleParser implements Parser {
	private Logger logger = Logger.getLogger(getClass());

	public SimpleParser() {
		
	}
	
	/**
	 * TODO Sempurnakan method ini biar bisa nge-handle advance use case.
	 * 		Misalnya string normalization (auto-correct) atau nilai tambah lainnya.
	 */
	@Override
	public Result parse(String payload, Format format) throws ParsingException {
		Result result=null;
		switch (format.getPayloadType()) {
		case SIMPLE_STRING:
			result=parseSimple(payload, format);
			break;
		case MIX_STRING:
			result=parseMulti(payload, format);
			break;
		case MAPPED:
			result=parseMap(payload, format);
			break;
		default:
			break;
		}
		return result;
	}
	
	protected Result parseSimple(String payload,Format format){
		Format parsedFormat=null;
		Result result=null;
		String keyword=null;
		Set<Parameter<?>> parameters=new HashSet<Parameter<?>>();
		logger.info("Parsing message payload: "+payload);
		Sampler sampler=SamplerFactory.getSamplerForType(SamplerType.SIMPLE);
		parsedFormat=sampler.createFromSample(payload);
		if(parsedFormat.getModel().countParameters()!=format.getModel().countParameters()){
			logger.error("Invalid message. Message part not matched with current format");
			throw new ParsingException("Invalid message. Message part not matched with current format", this);
		}
		keyword=sampler.getKeywordPart();
		for(int i=1;i<=parsedFormat.getModel().countParameters();i++){
			String parameterName=parsedFormat.getModel().getParameterNameAt(i+1);
			String parameterValue=sampler.getParameterValueAt(i);
			Class<? extends Parameter<?>> parameterType=parsedFormat.getModel().getParameterTypeAt(i+1);
			logger.debug("parameter name: "+parameterName+", parameter value: "+parameterValue+", parameter type: "+parameterType.getSimpleName());
			if(StringParameter.class.isAssignableFrom(parameterType)){
				StringParameter parameter=new StringParameter(parameterName, parameterValue);
				parameters.add(parameter);
			}else if(NumberParameter.class.isAssignableFrom(parameterType)){
				Integer intValue=Integer.parseInt(parameterValue);
				NumberParameter parameter=new NumberParameter(parameterName, intValue);
				parameters.add(parameter);
			}
		}
		
		result = new Result(keyword, parameters);
		
		logger.info("Parsing result : " + result);
		return result;
	}
	
	protected Result parseMulti(String payload,Format format){
		Format parsedFormat=null;
		Result result=null;
		String keyword=null;
		Set<Parameter<?>> parameters=new HashSet<Parameter<?>>();
		logger.info("Parsing message payload: "+payload);
		Sampler sampler=SamplerFactory.getSamplerForType(SamplerType.MULTI);
		parsedFormat=sampler.createFromSample(payload);
		if(parsedFormat.getModel().countParameters()!=format.getModel().countParameters()){
			logger.error("Invalid message. Message part not matched with current format");
			throw new ParsingException("Invalid message. Message part not matched with current format", this);
		}
		keyword=sampler.getKeywordPart();
		for(int i=1;i<=parsedFormat.getModel().countParameters();i++){
			String parameterName=parsedFormat.getModel().getParameterNameAt(i+1);
			String parameterValue=sampler.getParameterValueAt(i);
			Class<? extends Parameter<?>> parameterType=parsedFormat.getModel().getParameterTypeAt(i+1);
			logger.debug("parameter name: "+parameterName+", parameter value: "+parameterValue+", parameter type: "+parameterType.getSimpleName());
			if(StringParameter.class.isAssignableFrom(parameterType)){
				StringParameter parameter=new StringParameter(parameterName, parameterValue);
				parameters.add(parameter);
			}else if(NumberParameter.class.isAssignableFrom(parameterType)){
				Integer intValue=Integer.parseInt(parameterValue);
				NumberParameter parameter=new NumberParameter(parameterName, intValue);
				parameters.add(parameter);
			}
		}
		
		result = new Result(keyword, parameters);
		
		logger.info("Parsing result : " + result);
		return result;
	}
	
	protected Result parseMap(String payload,Format format){
		Format parsedFormat=null;
		Result result=null;
		String keyword=null;
		Set<Parameter<?>> parameters=new HashSet<Parameter<?>>();
		logger.info("Parsing message payload: "+payload);
		Sampler sampler=SamplerFactory.getSamplerForType(SamplerType.MAPPED);
		parsedFormat=sampler.createFromSample(payload);
		
		keyword=sampler.getKeywordPart();
		Map<String, Parameter<?>> mapParam=new HashMap<String, Parameter<?>>();
		for(int i=2;i<=parsedFormat.getModel().countParameters();i++){
			String parameterName=parsedFormat.getModel().getParameterNameAt(i+1);
			Class<? extends Parameter<?>> parameterType=parsedFormat.getModel().getParameterTypeAt(i+1);
			String parameterValue=sampler.getParameterStringFor(parameterName);
			logger.info("parameter name: "+parameterName+", parameter value: "+parameterValue+", parameter type: "+parameterType.getSimpleName());
			if(parameterValue!=null && parameterType!=null && parameterName!=null){
				logger.debug("parameter name: "+parameterName+", parameter value: "+parameterValue+", parameter type: "+parameterType.getSimpleName());
				if(StringParameter.class.isAssignableFrom(parameterType)){
					StringParameter parameter=new StringParameter(parameterName, parameterValue);
					mapParam.put(parameter.getName(), parameter);
				}else if(NumberParameter.class.isAssignableFrom(parameterType)){
					Integer intValue=Integer.parseInt(parameterValue);
					NumberParameter parameter=new NumberParameter(parameterName, intValue);
					mapParam.put(parameter.getName(), parameter);
				}
			}
			
		}
		MapParameter mapParameter=new MapParameter(keyword+"map", mapParam);
		parameters.add(mapParameter);
		
		result = new Result(keyword, parameters);
		
		logger.info("Parsing result : " + result);
		return result;
	}
}
