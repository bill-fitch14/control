/**
  * This file was generated from model [Model1] on [Fri Aug 26 23:04:33 BST 2016].
  * Do not change content of this file.
  */

#include "Model1ModelEngine.h"

CModel1ModelEngine* CModel1ModelEngine::NewL(
        unimod.C1* aO1,
        unimod.C2* aO2)
    {
    CModel1ModelEngine* self = CModel1ModelEngine::NewLC(
        aO1, aO2); 
	CleanupStack::Pop(self);
    return self;
    }

CModel1ModelEngine* CModel1ModelEngine::NewLC(
        unimod.C1* aO1,
        unimod.C2* aO2)
    {
    CModel1ModelEngine* self = new (ELeave) CModel1ModelEngine;
    CleanupStack::PushL(self);
    self->ConstructL(
        aO1, aO2); 
    return self;
    }

void CModel1ModelEngine::ConstructL(
        unimod.C1* aO1,
        unimod.C2* aO2)
	{
	iA1 = new (ELeave) TA1(*this);

	iA1->SetO1(aO1);
	iA1->SetO2(aO2);
	}

CModel1ModelEngine::~CModel1ModelEngine()
	{
	if (iA1)
		{
		delete iA1;
		iA1 = NULL;
		}
	}

/*
 * Returns name of the given event
 */
const TDesC& CModel1ModelEngine::EventName(CModel1ModelEngine::EEvent anEvent) const
	{
	switch (anEvent)
	    {
	    case E_STOP:
			{
			_LIT(KEventName, "E_stop");
		    return KEventName;
			}
	    case E_INITIALISE:
			{
			_LIT(KEventName, "e_initialise");
		    return KEventName;
			}
	    case E_PROCESS_POINT_REQUEST:
			{
			_LIT(KEventName, "e_process_point_request");
		    return KEventName;
			}
	    case E_PROCESS_QUEUE:
			{
			_LIT(KEventName, "e_process_queue");
		    return KEventName;
			}
	    case E_READ_STACK:
			{
			_LIT(KEventName, "e_read_stack");
		    return KEventName;
			}
	    case E_REMOVE_STOP:
			{
			_LIT(KEventName, "e_remove_stop");
		    return KEventName;
			}
	    case E_SET_TRAIN_POSITION:
			{
			_LIT(KEventName, "e_setTrainPosition");
		    return KEventName;
			}
	    case E_TRACK_POSITION:
			{
			_LIT(KEventName, "e_track_position");
		    return KEventName;
			}

        default:
			{
			_LIT(KEventName, "*");
			return KEventName;
			}
		}
	}

void CModel1ModelEngine::HandleL(CModel1ModelEngine::EEvent aEvent)
	{
	iA1->HandleL(aEvent, iContext);
	}
void CModel1BaseMachine::DoLogL(TDesC& message)
	{
	TBuf8<128> message8;
	message8.Copy(message);
	iModel.Log()->WriteL(message8);
	iModel.Log()->CommitL();
	}

void CModel1BaseMachine::LogBeginL(const TDesC& aStateName, CAutoAnswererModelEngine::EEvent aEvent)
	{
	_LIT(KFormat, "%S:[%S] on %S\n");
	TBuf<128> message;
	message.Format(KFormat, &MachineName(), &aStateName, &iModel.EventName(aEvent));
	DoLogL(message);
	}
	
void CModel1BaseMachine::LogTransiteL(const TDesC& aOldStateName, const TDesC& aNewStateName)
	{
	_LIT(KFormat, "  %S:[%S] => [%S]\n");
	TBuf<128> message;
	message.Format(KFormat, &MachineName(), &aOldStateName, &aNewStateName);
	DoLogL(message);
	}
	
void CModel1BaseMachine::LogEndL(const TDesC& aStateName, CAutoAnswererModelEngine::EEvent /*aEvent*/)
	{
	_LIT(KFormat, "%S:[%S]\n\n");
	TBuf<128> message;
	message.Format(KFormat, &MachineName(), &aStateName);
	DoLogL(message);
	}
	
void CModel1BaseMachine::LogActionBeginL(const TDesC& anIdentifier)
	{
	_LIT(KFormat, "  %S {\n");
	TBuf<128> message;
	message.Format(KFormat, &anIdentifier);
	DoLogL(message);
	}
	
void CModel1BaseMachine::LogActionEndL(const TDesC& anIdentifier)
	{
	_LIT(KFormat, "  %S }\n");
	TBuf<128> message;
	message.Format(KFormat, &anIdentifier);
	DoLogL(message);
	}
	
void CModel1BaseMachine::LogActionEndL(const TDesC& anIdentifier, TInt aValue)
	{
	_LIT(KFormat, "  %S = %d }\n");
	TBuf<128> message;
	message.Format(KFormat, &anIdentifier, aValue);
	DoLogL(message);
	}


///////////////////////////////////////////////////////////////////////////
// Automaton A1 section
///////////////////////////////////////////////////////////////////////////

/*
 * Executes on enter actions of the given state of automaton A1
 */
void TA1::EnterStateL(TA1::EState aState, TStateMachineContext& aContext)
	{
	switch (aState)
	    {
	    case S2:
			break;
	    case TOP:
			break;
	    case PROCESS_POINTS:
			break;
	    case SET_POINTS:
			break;
	    case SET_TRAIN_POSITION:
			break;
		}
	}

/*
 * Checks whether given state of automaton A1 is stable.
 */
TBool TA1::IsStable(TA1::EState aState) const
	{
	return 
        aState == TA1::EState::S2 ||
        aState == TA1::EState::PROCESS_POINTS ||
        aState == TA1::EState::SET_POINTS ||
        aState == TA1::EState::SET_TRAIN_POSITION ||
        FALSE;
	}

/*
 * Returns superstate of the given state of automaton A1
 */
const TDesC& TA1::StateName(TA1::EState aState) const
	{
	switch (aState)
	    {
	    case S2:
			{
			_LIT(KStateName, "s2");
		    return KStateName;
			}
	    case TOP:
			{
			_LIT(KStateName, "Top");
		    return KStateName;
			}
	    case PROCESS_POINTS:
			{
			_LIT(KStateName, "process points");
		    return KStateName;
			}
	    case SET_POINTS:
			{
			_LIT(KStateName, "set points");
		    return KStateName;
			}
	    case SET_TRAIN_POSITION:
			{
			_LIT(KStateName, "set train position");
		    return KStateName;
			}
        default:
			{
			_LIT(KStateName, "<<Unknown>>");
			return KStateName;
			}
		}
	}

/*
 * Returns name of the machine
 */
const TDesC& TA1::MachineName() const
	{
	_LIT(KName, "A1");
    return KName;
	}

/*
 * Returns superstate of the given state of automaton A1
 */
TA1::EState TA1::GetSuperstate(TA1::EState aState) const
	{
	switch (aState)
	    {
	    case S2:
		    return TOP;
	    case PROCESS_POINTS:
		    return TOP;
	    case SET_POINTS:
		    return TOP;
	    case SET_TRAIN_POSITION:
		    return TOP;
        default:
			// For top and unexisting states
			return UNKNOWN_STATE;
		}
	}

/*
 * For stable state immediately returns itself. For unstable state transites
 * to corresponding stable invoking all actions on transitions.
 */
TA1::EState TA1::TransiteToStableL(TA1::EState aState, TStateMachineContext& aContext)
    {	
	EState newState = aState;
	while (!IsStable(newState)) 
        {
		switch (newState)
		    {
            case TOP:				
                newState = PROCESS_POINTS;
                LogTransiteL(StateName(S1), StateName(newState));
                EnterStateL(newState, aContext);
			    break;
			default: ;// Impossible case
			}
        }
	return newState;
	}

/*
 * Selects transition and transites.
 */
TA1::EState TA1::TransiteOnEventL(CModel1ModelEngine::EEvent aEvent, TStateMachineContext& aContext)
	{
	TA1::EState targetState = UNKNOWN_STATE;
	for (TA1::EState sourceState = iState; targetState == UNKNOWN_STATE && sourceState != TOP; sourceState = GetSuperstate(sourceState))
		{
        switch (sourceState) 
    	    {
            /* State: s2 */
            case S2:
                switch (aEvent)
    			    {
                    default: ;// Do nothing if no event was triggered
					}
			    break;
            /* State: Top */
            case TOP:
                switch (aEvent)
    			    {
                    default: ;// Do nothing if no event was triggered
					}
			    break;
            /* State: process points */
            case PROCESS_POINTS:
                switch (aEvent)
    			    {
					case CModel1ModelEngine::EEvent::E_STOP:
                        targetState = S2;
                        LogTransiteL(StateName(PROCESS_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_INITIALISE:
                        targetState = SET_POINTS;
                        LogTransiteL(StateName(PROCESS_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_PROCESS_POINT_REQUEST:
                        iO1->ZPrintCurrentRequestL(aContext);
                        iO1->ZAddRequestToQueueL(aContext);
                        targetState = PROCESS_POINTS;
                        LogTransiteL(StateName(PROCESS_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_PROCESS_QUEUE:
                        iO1->ZProcessRequestQueueL(aContext);
                        targetState = PROCESS_POINTS;
                        LogTransiteL(StateName(PROCESS_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_READ_STACK:
                        iO2->ZProcessStackL(aContext);
                        targetState = PROCESS_POINTS;
                        LogTransiteL(StateName(PROCESS_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_SET_TRAIN_POSITION:
                        iO1->InitTrainNoL(aContext);
                        targetState = SET_TRAIN_POSITION;
                        LogTransiteL(StateName(PROCESS_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
                    default: ;// Do nothing if no event was triggered
					}
			    break;
            /* State: set points */
            case SET_POINTS:
                switch (aEvent)
    			    {
					case CModel1ModelEngine::EEvent::E_REMOVE_STOP:
                        iO1->ZRemoveFromJtreeL(aContext);
                        targetState = PROCESS_POINTS;
                        LogTransiteL(StateName(SET_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_TRACK_POSITION:
                        iO1->ZAddToJtreeL(aContext);
                        targetState = PROCESS_POINTS;
                        LogTransiteL(StateName(SET_POINTS), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
                    default: ;// Do nothing if no event was triggered
					}
			    break;
            /* State: set train position */
            case SET_TRAIN_POSITION:
                switch (aEvent)
    			    {
					case CModel1ModelEngine::EEvent::E_REMOVE_STOP:
                        targetState = PROCESS_POINTS;
                        LogTransiteL(StateName(SET_TRAIN_POSITION), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_TRACK_POSITION:
                        iO1->PositionTrainL(aContext);
                        targetState = PROCESS_POINTS;
                        LogTransiteL(StateName(SET_TRAIN_POSITION), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
                    default: ;// Do nothing if no event was triggered
					}
			    break;
			}
		}
		
	// Look for default transition
	for (TA1::EState sourceState = iState; targetState == UNKNOWN_STATE && sourceState != TOP; sourceState = GetSuperstate(sourceState))
		{
        switch (sourceState) 
    	    {
            /* State: s2 */
            case S2:
			    break;
            /* State: Top */
            case TOP:
			    break;
            /* State: process points */
            case PROCESS_POINTS:
			    break;
            /* State: set points */
            case SET_POINTS:
			    break;
            /* State: set train position */
            case SET_TRAIN_POSITION:
                targetState = SET_TRAIN_POSITION;
                LogTransiteL(StateName(SET_TRAIN_POSITION), StateName(targetState));
                EnterStateL(targetState, aContext);
			    break;
			}
		}
	
	// If no transition was found stay in current state
	return targetState != UNKNOWN_STATE ? targetState : iState;
	}

void TA1::InvokeSubmachinesL(CModel1ModelEngine::EEvent aEvent, TStateMachineContext& aContext)
	{
	for (EState curState = iState; curState != TOP; curState = GetSuperstate(curState))
		{
        switch (curState)
    	    {
            /* State: s2 */
            case S2:
			    break;
            /* State: Top */
            case TOP:
			    break;
            /* State: process points */
            case PROCESS_POINTS:
			    break;
            /* State: set points */
            case SET_POINTS:
			    break;
            /* State: set train position */
            case SET_TRAIN_POSITION:
			    break;
			}
		}
	}

void TA1::HandleL(CModel1ModelEngine::EEvent aEvent, TStateMachineContext& aContext)
	{
	// Init state machine A1 if it is in its top state
	if (iState == EState::TOP) 
		{
		iState = TransiteToStableL(EState::TOP, aContext);
		}
	
	LogBeginL(StateName(iState), aEvent);
	EState newState = TransiteOnEventL(aEvent, aContext);
	iState = TransiteToStableL(newState, aContext);
	InvokeSubmachinesL(aEvent, aContext);
	LogEndL(StateName(iState), aEvent);
	}

