----------------------------- MODULE EdTwoPhase -----------------------------
CONSTANT RMs

VARIABLES msgs, tmState, tmPrepared, rmState


(****************************************************************************
msgs         List of Resource Managers (RMs) that have commited
tmState      State of TM (either "init", "commit", "abort")
tmPrepared   The RMs the TM has recieved the status of "Prepared" from
rmState      Is a list of all the resource managers's statuses
****************************************************************************)


\* Set of possible messages
Messages ==
    [type : {"Prepared"}, r: RMs] \cup [type: {"Commit", "Abort"}]
  
\* Initialize all the states
Init ==
    /\ msgs = {}
    /\ tmPrepared = {}
    /\ tmState = {"Init"}
    /\ rmState = [r \in RMs |-> "working"]

\* Prepare the rm to go from "working" to "prepared"
RMPrepare(rm) ==
    /\ rmState[rm] = "working"
    /\ rmState' = [rmState EXCEPT![rm] = "prepared"] \* everything allowed but RMs should be in working state now
    /\ tmPrepared' = tmPrepared \cup {rm}
    /\ UNCHANGED <<tmState, tmPrepared>>
    

(*
 * Describes the receipt of a Prepared message from RM r by TM
 * The 
 
*)

TMCommit == 
    /\ tmState = "init"
    /\ tmPrepared = RMs
    /\ UNCHANGED <<rmState, tmPrepared>>
   
(*
 * Describes the receipt of a Prepared message from RM r by TM
 * The 
 
*)

TMRcvPrepared(r) == 
    \* Enabling conditions (even though they don't change (TLC requires)
    /\ tmState = "init"
    /\ [type |-> "Prepared", rm |-> r] \in msgs
    /\ tmPrepared'  = tmPrepared \cup {r} \* this adds r to the set of tmPrepared
    /\ UNCHANGED <<tmState, rmState, msgs>>
    
(*
 * When in its "working" state, RM r can go to the "aborted" state in a silent abort
 *)
 
RMChooseToAbort(rm) == 
    /\ rmState[rm] = "working"
    /\ rmState' = [rmState EXCEPT![rm] = "aborted"]
    /\ UNCHANGED <<msgs, tmState, tmPrepared>>


RM

 
TMAbort == 
    /\ tmState = "init"
    /\ tmState' = "abort"
    /\ msgs' = msgs \cup {[type |-> "Abort"]} \* Message about to be sent by TM
    /\ UNCHANGED <<tmPrepared, rmState, msgs>>

TPTypeOK == 
    /\ rmState \in {"working","prepared", "committed","aborted"} \* Is this right?
    /\ msgs \in SUBSET Messages
    /\ tmState \in {"init","commit","aborted"}
    /\ tmPrepared \in SUBSET RMs
    




=============================================================================
\* Modification History
\* Last modified Fri May 31 15:54:30 EDT 2024 by eddie
\* Created Thu May 30 14:48:59 EDT 2024 by eddie
