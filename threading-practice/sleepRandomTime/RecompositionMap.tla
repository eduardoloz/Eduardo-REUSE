---- MODULE RecompositionMap ----
EXTENDS Naturals, Sequences, TLC

CONSTANTS N \* Number of threads
VARIABLES globalIsDone, threads, done

Init == 
    /\ globalIsDone = FALSE
    /\ threads = << >>
    /\ done = [ t \in 1..N |-> FALSE ]

Thread(i) == 
    /\ done[i] = FALSE
    /\ UNCHANGED << globalIsDone, done, threads >>

Sleep(i) == 
    /\ done[i] = FALSE
    /\ threads' = Append(threads, i)
    /\ UNCHANGED << globalIsDone, done >>

Check(i) == 
    /\ done[i] = FALSE
    /\ globalIsDone = FALSE
    /\ globalIsDone' = TRUE
    /\ done' = [ done EXCEPT ![i] = TRUE ]
    /\ UNCHANGED threads

AlreadyDone(i) == 
    /\ done[i] = FALSE
    /\ globalIsDone = TRUE
    /\ done' = [ done EXCEPT ![i] = TRUE ]
    /\ UNCHANGED << globalIsDone, threads >>

Next == 
    \E i \in 1..N: 
        \/ Thread(i)
        \/ Sleep(i)
        \/ Check(i)
        \/ AlreadyDone(i)

Spec == Init /\ [][Next]_<< globalIsDone, threads, done >>

\* Properties to check
Termination == 
    \A i \in 1..N: done[i]

Safety == 
    \A i, j \in 1..N: 
        i /= j => done[i] /\ done[j] => globalIsDone = TRUE

====
