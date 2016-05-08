/**
  * This file was generated from model [Model1] on [Sat Feb 20 00:23:36 GMT 2016].
  * Do not change content of this file.
  */

#include "Model1ModelEngine.h"

CModel1ModelEngine* CModel1ModelEngine::NewL(
        sm2.C1* aO1)
    {
    CModel1ModelEngine* self = CModel1ModelEngine::NewLC(
        aO1); 
	CleanupStack::Pop(self);
    return self;
    }

CModel1ModelEngine* CModel1ModelEngine::NewLC(
        sm2.C1* aO1)
    {
    CModel1ModelEngine* self = new (ELeave) CModel1ModelEngine;
    CleanupStack::PushL(self);
    self->ConstructL(
        aO1); 
    return self;
    }

void CModel1ModelEngine::ConstructL(
        sm2.C1* aO1)
	{
	iA1 = new (ELeave) TA1(*this);

	iA1->SetO1(aO1);
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
	    case E_PROCESS_POINT_REQUEST:
			{
			_LIT(KEventName, "e_process_point_request");
		    return KEventName;
			}
	    case E_READ_POINTS_STACK:
			{
			_LIT(KEventName, "e_read_points_stack");
		    return KEventName;
			}
	    case E_READ_STACK:
			{
			_LIT(KEventName, "e_read_stack");
		    return KEventName;
			}
	    case E_STOP_STACK:
			{
			_LIT(KEventName, "e_stop_stack");
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
	    case STACK:
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
        aState == TA1::EState::STACK ||
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
	    case STACK:
			{
			_LIT(KStateName, "stack");
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
	    case STACK:
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
                iO1->ZStartStackL(aContext);
                newState = STACK;
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
            /* State: stack */
            case STACK:
                switch (aEvent)
    			    {
					case CModel1ModelEngine::EEvent::E_PROCESS_POINT_REQUEST:
                        iO1->ZPrintCurrentRequestL(aContext);
                        iO1->ZAddRequestToQueueL(aContext);
                        targetState = STACK;
                        LogTransiteL(StateName(STACK), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_READ_POINTS_STACK:
                        iO1->ZProcessRequestQueueL(aContext);
                        targetState = STACK;
                        LogTransiteL(StateName(STACK), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_READ_STACK:
                        iO1->ZProcessStackL(aContext);
                        targetState = STACK;
                        LogTransiteL(StateName(STACK), StateName(targetState));
                        EnterStateL(targetState, aContext);
	                    break;
					case CModel1ModelEngine::EEvent::E_STOP_STACK:
                        targetState = S2;
                        LogTransiteL(StateName(STACK), StateName(targetState));
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
            /* State: stack */
            case STACK:
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
            /* State: stack */
            case STACK:
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

