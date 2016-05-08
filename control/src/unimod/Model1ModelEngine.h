/**
  * This file was generated from model [Model1] on [Sat Feb 20 00:23:35 GMT 2016].
  * Do not change content of this file.
  */

#ifndef __Model1ModelEngine_H__
#define __Model1ModelEngine_H__

#include <e32def.h>
#include <f32file.h>
#include <s32file.h>

/* Forward declaration of model engine class */	
class CModel1ModelEngine;
/* Forward declaration of base state machine class */	
class CModel1BaseMachine;
/* Forward declaration of state machines from the model */	
class TA1;

/* Include state machine context class header */	
#include "StateMachineContext.h"

/* Include used controlled objects classes headers */	
#include "unimod.C1.h"
#include "unimod.C2.h"

/******************************************
 Model engine class: Model1
 ******************************************/
class CModel1ModelEngine
    {
		friend class TA1;

	public:
		static CModel1ModelEngine* NewL(
            unimod.C1* aO1,
            unimod.C2* aO2);

		static CModel1ModelEngine* NewLC(
            unimod.C1* aO1,
            unimod.C2* aO2);
		
		virtual ~CModel1ModelEngine();

	private:

		void ConstructL(
            unimod.C1* aO1,
            unimod.C2* aO2);

	public:
		enum EEvent
            {
            E_STOP,
            E_INITIALISE,
            E_PROCESS_POINT_REQUEST,
            E_PROCESS_QUEUE,
            E_READ_STACK,
            E_REMOVE_STOP,
            E_SET_TRAIN_POSITION,
            E_TRACK_POSITION,
            ANY_OTHER
            };
	
		void HandleL(EEvent aEvent);
		
		TStateMachineContext& Context() {return iContext;}
				
		RWriteStream* Log() {return iLog;}

		const TDesC& EventName(EEvent anEvent) const;
		
	private:
		// State machine context
		TStateMachineContext iContext;
	
	    // Statemachines are owned by CModel1ModelEngine
		TA1* iA1;
		
		// Logger stream isn't owned by CAutoAnswererModelEngine
		RWriteStream* iLog;
	};

/**
 * Base state machine class for model: Model1
 */
class CModel1BaseMachine
	{
	public:
		/* Constructor of base state machine */
		CModel1BaseMachine(CModel1ModelEngine& aModel): iModel(aModel) {}
				
        virtual void HandleL(CModel1ModelEngine::EEvent aEvent, TStateMachineContext& aContext) = 0;

	protected:
		CModel1ModelEngine& iModel;
		
		virtual const TDesC& MachineName() const = 0;
		
		/**
		 * Logging methods
		 */
		void DoLogL(TDesC& message);
		void LogBeginL(const TDesC& aStateName, CAutoAnswererModelEngine::EEvent aEvent);
		void LogTransiteL(const TDesC& aOldStateName, const TDesC& aNewStateName);
		void LogEndL(const TDesC& aStateName, CAutoAnswererModelEngine::EEvent aEvent);
		void LogActionBeginL(const TDesC& anIdentifier);
		void LogActionEndL(const TDesC& anIdentifier);
		void LogActionEndL(const TDesC& anIdentifier, TInt aValue);
	};

/******************************************
 State machine class: A1          
 ******************************************/
class TA1: CModel1BaseMachine
	{
	public:
		/* Constructor of state machine A1 */
		TA1(CModel1ModelEngine& aModel): CModel1BaseMachine(aModel), iState(TOP) {}
		
        void HandleL(CModel1ModelEngine::EEvent aEvent, TStateMachineContext& aContext);
		
        /* Controlled objects setters */
		void SetO1(unimod.C1* aO1) {iO1 = aO1;}
		void SetO2(unimod.C2* aO2) {iO2 = aO2;}


	private:
		enum EState 
            {
            S2,
            TOP,
            PROCESS_POINTS,
            SET_POINTS,
            SET_TRAIN_POSITION,
            UNKNOWN_STATE
            };

        EState iState;

        /* Controlled objects */
		unimod.C1* iO1;
		unimod.C2* iO2;


		TBool IsStable(EState aState) const;
		EState GetSuperstate(EState aState) const;
		const TDesC& StateName(EState aState) const; 
		const TDesC& MachineName() const;
		EState TransiteOnEventL(CModel1ModelEngine::EEvent aEvent, TStateMachineContext& aContext);
		EState TransiteToStableL(EState aState, TStateMachineContext& aContext);
		void EnterStateL(EState aState, TStateMachineContext& aContext);
		void InvokeSubmachinesL(CModel1ModelEngine::EEvent aEvent, TStateMachineContext& aContext);
	};

#endif // __Model1ModelEngine_H__