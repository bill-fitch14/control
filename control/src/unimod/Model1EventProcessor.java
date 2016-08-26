/**
  * This file was generated from model [Model1] on [Fri Aug 26 23:04:36 BST 2016].
  * Do not change content of this file.
  */
package unimod;

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
	        private unimod.C2 o2 = null;
	        private unimod.C1 o1 = null;
	        private unimod.E1 p1 = null;
	    
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
                if (StringUtils.equals(coName, "o2")) {
                		    if (o2 == null) {
                		        o2 = new unimod.C2();
                		    }
                		    return o2;
                }
                if (StringUtils.equals(coName, "o1")) {
                		    if (o1 == null) {
                		        o1 = new unimod.C1();
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
                		        p1 = new unimod.E1();
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
	    private static final int process_points = 4;
	    private static final int set_points = 5;
	    private static final int set_train_position = 6;
	
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
				
		if ("process points".equals(state)) {
			return process_points;
						} else 
				
		if ("set points".equals(state)) {
			return set_points;
						} else 
				
		if ("set train position".equals(state)) {
			return set_train_position;
			}
		
		return -1;
	}
	
		// events
	    private static final int e_initialise = 1;
	    private static final int E_stop = 2;
	    private static final int e_read_stack = 3;
	    private static final int e_process_queue = 4;
	    private static final int e_track_position = 5;
	    private static final int e_setTrainPosition = 6;
	    private static final int e_remove_stop = 7;
	    private static final int e_process_point_request = 8;
	
	private int decodeEvent(String event) {
					
		if ("e_initialise".equals(event)) {
			return e_initialise;
						} else 
				
		if ("E_stop".equals(event)) {
			return E_stop;
						} else 
				
		if ("e_read_stack".equals(event)) {
			return e_read_stack;
						} else 
				
		if ("e_process_queue".equals(event)) {
			return e_process_queue;
						} else 
				
		if ("e_track_position".equals(event)) {
			return e_track_position;
						} else 
				
		if ("e_setTrainPosition".equals(event)) {
			return e_setTrainPosition;
						} else 
				
		if ("e_remove_stop".equals(event)) {
			return e_remove_stop;
						} else 
				
		if ("e_process_point_request".equals(event)) {
			return e_process_point_request;
				}
	
		return -1;
	}
		
							private unimod.C2 o2;
								private unimod.C1 o1;
				
	private void init(ControlledObjectsMap controlledObjectsMap) {
									o2 = (unimod.C2)controlledObjectsMap.getControlledObject("o2");
												o1 = (unimod.C1)controlledObjectsMap.getControlledObject("o1");
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
																					case process_points: 
					
											return;
																					case set_points: 
					
											return;
																					case set_train_position: 
					
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

					// s1->process points [true]/
					event = Event.NO_EVENT;
						fireTransitionFound(context, path, "s1", event, "s1#process points##true");
	
		
	fireComeToState(context, path, "process points");
	
	// process points []
						
											return new StateMachineConfig("process points");
																																					}
	
		return config;
	}    
	
	private StateMachineConfig lookForTransition(Event event, StateMachineContext context, StateMachinePath path, StateMachineConfig config) throws Exception {
					
		
				
		
	
				BitSet calculatedInputActions = new BitSet(0);
		
		int s = decodeState(config.getActiveState());
		int e = decodeEvent(event.getName());
	
		while (true) {
			switch (s) {
																																	case process_points:
						
											
												switch (e) {
													case e_initialise:
																
						// process points->set points e_initialise[true]/
			
			fireTransitionCandidate(context, path, "process points", event, "process points#set points#e_initialise#true");
			
						
							
			
							
					fireTransitionFound(context, path, "process points", event, "process points#set points#e_initialise#true");
	
		
	fireComeToState(context, path, "set points");
	
	// set points []
					return new StateMachineConfig("set points");

																				
														case E_stop:
																
						// process points->s2 E_stop[true]/
			
			fireTransitionCandidate(context, path, "process points", event, "process points#s2#E_stop#true");
			
						
							
			
							
					fireTransitionFound(context, path, "process points", event, "process points#s2#E_stop#true");
	
		
	fireComeToState(context, path, "s2");
	
	// s2 []
					return new StateMachineConfig("s2");

																				
														case e_read_stack:
																
						// process points->process points e_read_stack[true]/o2.z_process_stack
			
			fireTransitionCandidate(context, path, "process points", event, "process points#process points#e_read_stack#true");
			
						
							
			
							
					fireTransitionFound(context, path, "process points", event, "process points#process points#e_read_stack#true");
	
				fireBeforeOutputActionExecution(context, path, "process points#process points#e_read_stack#true", "o2.z_process_stack");

				o2.z_process_stack(context);
		
	fireAfterOutputActionExecution(context, path, "process points#process points#e_read_stack#true", "o2.z_process_stack");
		
	fireComeToState(context, path, "process points");
	
	// process points []
					return new StateMachineConfig("process points");

																				
														case e_process_queue:
																
						// process points->process points e_process_queue[true]/o1.z_processRequestQueue
			
			fireTransitionCandidate(context, path, "process points", event, "process points#process points#e_process_queue#true");
			
						
							
			
							
					fireTransitionFound(context, path, "process points", event, "process points#process points#e_process_queue#true");
	
				fireBeforeOutputActionExecution(context, path, "process points#process points#e_process_queue#true", "o1.z_processRequestQueue");

				o1.z_processRequestQueue(context);
		
	fireAfterOutputActionExecution(context, path, "process points#process points#e_process_queue#true", "o1.z_processRequestQueue");
		
	fireComeToState(context, path, "process points");
	
	// process points []
					return new StateMachineConfig("process points");

																				
														case e_setTrainPosition:
																
						// process points->set train position e_setTrainPosition[true]/o1.init_train_no
			
			fireTransitionCandidate(context, path, "process points", event, "process points#set train position#e_setTrainPosition#true");
			
						
							
			
							
					fireTransitionFound(context, path, "process points", event, "process points#set train position#e_setTrainPosition#true");
	
				fireBeforeOutputActionExecution(context, path, "process points#set train position#e_setTrainPosition#true", "o1.init_train_no");

				o1.init_train_no(context);
		
	fireAfterOutputActionExecution(context, path, "process points#set train position#e_setTrainPosition#true", "o1.init_train_no");
		
	fireComeToState(context, path, "set train position");
	
	// set train position []
					return new StateMachineConfig("set train position");

																				
														case e_process_point_request:
																
						// process points->process points e_process_point_request[true]/o1.z_printCurrentRequest,o1.z_addRequestToQueue
			
			fireTransitionCandidate(context, path, "process points", event, "process points#process points#e_process_point_request#true");
			
						
							
			
							
					fireTransitionFound(context, path, "process points", event, "process points#process points#e_process_point_request#true");
	
				fireBeforeOutputActionExecution(context, path, "process points#process points#e_process_point_request#true", "o1.z_printCurrentRequest");

				o1.z_printCurrentRequest(context);
		
	fireAfterOutputActionExecution(context, path, "process points#process points#e_process_point_request#true", "o1.z_printCurrentRequest");
				fireBeforeOutputActionExecution(context, path, "process points#process points#e_process_point_request#true", "o1.z_addRequestToQueue");

				o1.z_addRequestToQueue(context);
		
	fireAfterOutputActionExecution(context, path, "process points#process points#e_process_point_request#true", "o1.z_addRequestToQueue");
		
	fireComeToState(context, path, "process points");
	
	// process points []
					return new StateMachineConfig("process points");

																				
													default:
														
							
										// transition not found
								return config;
												}
						
																						case set_points:
						
											
												switch (e) {
													case e_track_position:
																
						// set points->process points e_track_position[true]/o1.z_addToJtree
			
			fireTransitionCandidate(context, path, "set points", event, "set points#process points#e_track_position#true");
			
						
							
			
							
					fireTransitionFound(context, path, "set points", event, "set points#process points#e_track_position#true");
	
				fireBeforeOutputActionExecution(context, path, "set points#process points#e_track_position#true", "o1.z_addToJtree");

				o1.z_addToJtree(context);
		
	fireAfterOutputActionExecution(context, path, "set points#process points#e_track_position#true", "o1.z_addToJtree");
		
	fireComeToState(context, path, "process points");
	
	// process points []
					return new StateMachineConfig("process points");

																				
														case e_remove_stop:
																
						// set points->process points e_remove_stop[true]/o1.z_removeFromJtree
			
			fireTransitionCandidate(context, path, "set points", event, "set points#process points#e_remove_stop#true");
			
						
							
			
							
					fireTransitionFound(context, path, "set points", event, "set points#process points#e_remove_stop#true");
	
				fireBeforeOutputActionExecution(context, path, "set points#process points#e_remove_stop#true", "o1.z_removeFromJtree");

				o1.z_removeFromJtree(context);
		
	fireAfterOutputActionExecution(context, path, "set points#process points#e_remove_stop#true", "o1.z_removeFromJtree");
		
	fireComeToState(context, path, "process points");
	
	// process points []
					return new StateMachineConfig("process points");

																				
													default:
														
							
										// transition not found
								return config;
												}
						
																						case set_train_position:
						
											
												switch (e) {
													case e_track_position:
																
						// set train position->process points e_track_position[true]/o1.position_train
			
			fireTransitionCandidate(context, path, "set train position", event, "set train position#process points#e_track_position#true");
			
						
							
			
							
					fireTransitionFound(context, path, "set train position", event, "set train position#process points#e_track_position#true");
	
				fireBeforeOutputActionExecution(context, path, "set train position#process points#e_track_position#true", "o1.position_train");

				o1.position_train(context);
		
	fireAfterOutputActionExecution(context, path, "set train position#process points#e_track_position#true", "o1.position_train");
		
	fireComeToState(context, path, "process points");
	
	// process points []
					return new StateMachineConfig("process points");

																				
														case e_remove_stop:
																
						// set train position->process points e_remove_stop[true]/
			
			fireTransitionCandidate(context, path, "set train position", event, "set train position#process points#e_remove_stop#true");
			
						
							
			
							
					fireTransitionFound(context, path, "set train position", event, "set train position#process points#e_remove_stop#true");
	
		
	fireComeToState(context, path, "process points");
	
	// process points []
					return new StateMachineConfig("process points");

																				
													default:
														
						// set train position->set train position *[true]/
			
			fireTransitionCandidate(context, path, "set train position", event, "set train position#set train position#*#true");
			
						
							
			
							
					fireTransitionFound(context, path, "set train position", event, "set train position#set train position#*#true");
	
		
	fireComeToState(context, path, "set train position");
	
	// set train position []
					return new StateMachineConfig("set train position");

																				
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
	
