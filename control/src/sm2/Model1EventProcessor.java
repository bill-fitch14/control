/**
  * This file was generated from model [Model1] on [Thu Sep 01 23:07:23 BST 2016].
  * Do not change content of this file.
  */
package sm2;

import java.io.IOException;
import java.util.*;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.evelopers.common.exception.*;
import com.evelopers.unimod.core.stateworks.*;
import com.evelopers.unimod.debug.app.AppDebugger;
import com.evelopers.unimod.debug.protocol.JavaSpecificMessageCoder;
import com.evelopers.unimod.runtime.*;
import com.evelopers.unimod.runtime.context.*;
import com.evelopers.unimod.runtime.logger.SimpleLogger;


public class Model1EventProcessor extends AbstractEventProcessor {

	private ModelStructure modelStructure;

				private static final int A1 = 1;
	
	private int decodeStateMachine(String sm) {
					
		if ("A1".equals(sm)) {
			return A1;
			}

		return -1;
	}
		
			private A1EventProcessor _A1;
		
	public Model1EventProcessor() {
		modelStructure = new Model1ModelStructure();

					_A1 = new A1EventProcessor();
			}

		    public static void run(int debuggerPort, boolean debuggerSuspend) throws 
						            InterruptedException, EventProcessorException, CommonException, 
        			    IOException {

        		/* Create runtime engine */
        		ModelEngine engine = createModelEngine(true);

		        /* Setup logger */
		        final Log log = LogFactory.getLog(Model1EventProcessor.class);
        		engine.getEventProcessor().addEventProcessorListener(new SimpleLogger(log));

        		/* Setup exception handler */
        		engine.getEventProcessor().addExceptionHandler(new ExceptionHandler() {
		            public void handleException(StateMachineContext context, SystemException e) {
                				log.fatal(e.getChainedMessage(), e.getRootException());
		            }
		        });

        		if (debuggerPort > 0) {
            			AppDebugger d = new AppDebugger(
            					        debuggerPort, debuggerSuspend,
                    					new JavaSpecificMessageCoder(), engine);
                    			d.start();
            		}
            		engine.start();
        	}

    	public static void main(String[] args) throws Exception {
		        int debuggerPort = NumberUtils.stringToInt(System.getProperty("debugger.port"), -1);
        		boolean debuggerSuspend = BooleanUtils.toBoolean(System.getProperty("debugger.suspend"));
        		Model1EventProcessor.run(debuggerPort, debuggerSuspend);
    	}

    	public static ModelEngine createModelEngine(boolean useEventQueue) throws CommonException {
        	ObjectsManager objectsManager = new ObjectsManager();
        	return ModelEngine.createStandAlone(
        		        useEventQueue ? (EventManager) new QueuedHandler() : (EventManager) new StrictHandler(), 
        		        new Model1EventProcessor(),
                objectsManager.getControlledObjectsManager(),
                objectsManager.getEventProvidersManager());
	    }

	    public static class ObjectsManager {
	        private sm2.C1 o1 = null;
	        private sm2.E1 p1 = null;
	    
	        private ControlledObjectsManager controlledObjectsManager = new ControlledObjectsManagerImpl();
	        private EventProvidersManager eventProvidersManager = new EventProvidersManagerImpl();

	        public ControlledObjectsManager getControlledObjectsManager() {
	            return controlledObjectsManager;
	        }

	        public EventProvidersManager getEventProvidersManager() {
	            return eventProvidersManager;
	        }

	        private class ControlledObjectsManagerImpl implements ControlledObjectsManager {
	            public void init(ModelEngine engine) throws CommonException {}

	            public void dispose() {}

	            public ControlledObject getControlledObject(String coName) {
                if (StringUtils.equals(coName, "o1")) {
                		    if (o1 == null) {
                		        o1 = new sm2.C1();
                		    }
                		    return o1;
                }
	                throw new IllegalArgumentException("Controlled object with name [" + coName + "] wasn't found");
	            }
	        }

	        private class EventProvidersManagerImpl implements EventProvidersManager {
	            private List nonameEventProviders = new ArrayList();

	            public void init(ModelEngine engine) throws CommonException {
	                EventProvider ep;
	                ep = getEventProvider("p1");
                ep.init(engine);
	            }

	            public void dispose() {
	                	EventProvider ep;
		                ep = getEventProvider("p1");
                ep.dispose();
                for (Iterator i = nonameEventProviders.iterator(); i.hasNext();) {
                    ep = (EventProvider) i.next();
                    ep.dispose();
                }
            }

	            public EventProvider getEventProvider(String epName) {
	                if (StringUtils.equals(epName, "p1")) {
                		    if (p1 == null) {
                		        p1 = new sm2.E1();
                		    }
                		    return p1;
                }
                throw new IllegalArgumentException("Event provider with name [" + epName + "] wasn't found");
	            }
	        }
    	}
	
    public ModelStructure getModelStructure() {
		return modelStructure;
	}	
	
    public void setControlledObjectsMap(ControlledObjectsMap controlledObjectsMap) {
        super.setControlledObjectsMap(controlledObjectsMap);
		
					_A1.init(controlledObjectsMap);
		    }

	protected StateMachineConfig process(
            Event event, StateMachineContext context,
            StateMachinePath path, StateMachineConfig config) throws SystemException {

		// get state machine from path
		int sm = decodeStateMachine(path.getStateMachine());
		
		try {
		switch (sm) {
					case A1:
				return _A1.process(event, context, path, config);
					default:
				throw new EventProcessorException("Unknown state machine [" + path.getStateMachine() + "]");
		}
		} catch (Exception e) {
			if (e instanceof SystemException) {
				throw (SystemException)e;
			} else {
				throw new SystemException(e);
			}
		}
	}

    protected StateMachineConfig transiteToStableState(
            StateMachineContext context,
            StateMachinePath path, StateMachineConfig config) throws SystemException {

		// get state machine from path
		int sm = decodeStateMachine(path.getStateMachine());

		try {
		switch (sm) {
					case A1:
				return _A1.transiteToStableState(context, path, config);
					default:
				throw new EventProcessorException("Unknown state machine [" + path.getStateMachine() + "]");
		}
		} catch (Exception e) {
			if (e instanceof SystemException) {
				throw (SystemException)e;
			} else {
				throw new SystemException(e);
			}
		}
	}	

	
	
private class Model1ModelStructure implements ModelStructure {
	    private Map configManagers = new HashMap();

    private Model1ModelStructure() {
		        configManagers.put("A1", new com.evelopers.unimod.runtime.config.DistinguishConfigManager());
		    }

	    public StateMachinePath getRootPath() 
	            throws EventProcessorException {
	        	return new StateMachinePath("A1");				
	    }

	    public StateMachineConfigManager getConfigManager(String stateMachine) 
	            throws EventProcessorException {
		        return (StateMachineConfigManager)configManagers.get(stateMachine);
	    }

	    public StateMachineConfig getTopConfig(String stateMachine) 
	            throws EventProcessorException {
        		int sm = decodeStateMachine(stateMachine);
		
		        switch (sm) {
					            case A1:
                				return new StateMachineConfig("Top");
					            default:
                				throw new EventProcessorException("Unknown state machine [" + stateMachine + "]");
		        }
	    }

    	public boolean isFinal(String stateMachine, StateMachineConfig config) 
    	        throws EventProcessorException {
		        /* Get state machine from path */
        		int sm = decodeStateMachine(stateMachine);
        		int state;
		        		switch (sm) {
					            case A1:
                				state = _A1.decodeState(config.getActiveState());
				                switch (state) {
																																					                    case A1EventProcessor.s2:
																											                        return true;
				                    					default:
                    						    return false;
                	}
					            default:
                				throw new EventProcessorException("Unknown state machine [" + stateMachine + "]");
		        }
	    }			
}
	
			







private class A1EventProcessor {

		// states
	    private static final int Top = 1;
	    private static final int s1 = 2;
	    private static final int s2 = 3;
	    private static final int stack = 4;
	
	private int decodeState(String state) {
					
		if ("Top".equals(state)) {
			return Top;
						} else 
				
		if ("s1".equals(state)) {
			return s1;
						} else 
				
		if ("s2".equals(state)) {
			return s2;
						} else 
				
		if ("stack".equals(state)) {
			return stack;
			}
		
		return -1;
	}
	
		// events
	    private static final int e_read_stack = 1;
	    private static final int e_read_points_stack = 2;
	    private static final int e_stop_stack = 3;
	    private static final int e_process_point_request = 4;
	
	private int decodeEvent(String event) {
					
		if ("e_read_stack".equals(event)) {
			return e_read_stack;
						} else 
				
		if ("e_read_points_stack".equals(event)) {
			return e_read_points_stack;
						} else 
				
		if ("e_stop_stack".equals(event)) {
			return e_stop_stack;
						} else 
				
		if ("e_process_point_request".equals(event)) {
			return e_process_point_request;
				}
	
		return -1;
	}
		
							private sm2.C1 o1;
				
	private void init(ControlledObjectsMap controlledObjectsMap) {
									o1 = (sm2.C1)controlledObjectsMap.getControlledObject("o1");
						}
		
	private StateMachineConfig process(Event event, StateMachineContext context, StateMachinePath path, StateMachineConfig config) throws Exception {
		config = lookForTransition(event, context, path, config);
		
		config = transiteToStableState(context, path, config);
		
		// execute included state machines
		executeSubmachines(event, context, path, config);
		
		return config;
	}
	
	private void executeSubmachines(Event event, StateMachineContext context, StateMachinePath path, StateMachineConfig config) throws Exception {
		int state = decodeState(config.getActiveState());
		
		while (true) {
		switch (state) {
																			case s1: 
					
											return;
																					case s2: 
					
											return;
																					case stack: 
					
											return;
																default:
					throw new EventProcessorException("State with name [" + config.getActiveState() + "] is unknown for state machine [A1]");
		}
		}
	}
	
	private StateMachineConfig transiteToStableState(StateMachineContext context, StateMachinePath path, StateMachineConfig config) throws Exception {

		int s = decodeState(config.getActiveState());
		Event event;

		switch (s) {
									case Top:
					
										
					fireComeToState(context, path, "s1");

					// s1->stack [true]/o1.z_start_stack
					event = Event.NO_EVENT;
						fireTransitionFound(context, path, "s1", event, "s1#stack##true");
	
				fireBeforeOutputActionExecution(context, path, "s1#stack##true", "o1.z_start_stack");

				o1.z_start_stack(context);
		
	fireAfterOutputActionExecution(context, path, "s1#stack##true", "o1.z_start_stack");
		
	fireComeToState(context, path, "stack");
	
	// stack []
						
											return new StateMachineConfig("stack");
																											}
	
		return config;
	}    
	
	private StateMachineConfig lookForTransition(Event event, StateMachineContext context, StateMachinePath path, StateMachineConfig config) throws Exception {
					
		
				
		
	
				BitSet calculatedInputActions = new BitSet(0);
		
		int s = decodeState(config.getActiveState());
		int e = decodeEvent(event.getName());
	
		while (true) {
			switch (s) {
																																	case stack:
						
											
												switch (e) {
													case e_read_stack:
																
						// stack->stack e_read_stack[true]/o1.z_process_stack
			
			fireTransitionCandidate(context, path, "stack", event, "stack#stack#e_read_stack#true");
			
						
							
			
							
					fireTransitionFound(context, path, "stack", event, "stack#stack#e_read_stack#true");
	
				fireBeforeOutputActionExecution(context, path, "stack#stack#e_read_stack#true", "o1.z_process_stack");

				o1.z_process_stack(context);
		
	fireAfterOutputActionExecution(context, path, "stack#stack#e_read_stack#true", "o1.z_process_stack");
		
	fireComeToState(context, path, "stack");
	
	// stack []
					return new StateMachineConfig("stack");

																				
														case e_read_points_stack:
																
						// stack->stack e_read_points_stack[true]/o1.z_processRequestQueue
			
			fireTransitionCandidate(context, path, "stack", event, "stack#stack#e_read_points_stack#true");
			
						
							
			
							
					fireTransitionFound(context, path, "stack", event, "stack#stack#e_read_points_stack#true");
	
				fireBeforeOutputActionExecution(context, path, "stack#stack#e_read_points_stack#true", "o1.z_processRequestQueue");

				o1.z_processRequestQueue(context);
		
	fireAfterOutputActionExecution(context, path, "stack#stack#e_read_points_stack#true", "o1.z_processRequestQueue");
		
	fireComeToState(context, path, "stack");
	
	// stack []
					return new StateMachineConfig("stack");

																				
														case e_stop_stack:
																
						// stack->s2 e_stop_stack[true]/
			
			fireTransitionCandidate(context, path, "stack", event, "stack#s2#e_stop_stack#true");
			
						
							
			
							
					fireTransitionFound(context, path, "stack", event, "stack#s2#e_stop_stack#true");
	
		
	fireComeToState(context, path, "s2");
	
	// s2 []
					return new StateMachineConfig("s2");

																				
														case e_process_point_request:
																
						// stack->stack e_process_point_request[true]/o1.z_printCurrentRequest,o1.z_addRequestToQueue
			
			fireTransitionCandidate(context, path, "stack", event, "stack#stack#e_process_point_request#true");
			
						
							
			
							
					fireTransitionFound(context, path, "stack", event, "stack#stack#e_process_point_request#true");
	
				fireBeforeOutputActionExecution(context, path, "stack#stack#e_process_point_request#true", "o1.z_printCurrentRequest");

				o1.z_printCurrentRequest(context);
		
	fireAfterOutputActionExecution(context, path, "stack#stack#e_process_point_request#true", "o1.z_printCurrentRequest");
				fireBeforeOutputActionExecution(context, path, "stack#stack#e_process_point_request#true", "o1.z_addRequestToQueue");

				o1.z_addRequestToQueue(context);
		
	fireAfterOutputActionExecution(context, path, "stack#stack#e_process_point_request#true", "o1.z_addRequestToQueue");
		
	fireComeToState(context, path, "stack");
	
	// stack []
					return new StateMachineConfig("stack");

																				
													default:
														
							
										// transition not found
								return config;
												}
						
																	default:
					throw new EventProcessorException("Incorrect stable state [" + config.getActiveState() + "] in state machine [A1]");
			}
		}
	}

		        
}
	
	private static boolean isInputActionCalculated(BitSet calculatedInputActions, int k) {
		boolean b = calculatedInputActions.get(k);
            
        if (!b) {
			calculatedInputActions.set(k);
		}
            
        return b;
	}
	
}
	
