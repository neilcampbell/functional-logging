# Functional Logging

This is a PoC to explore how you might use a State monad (in the form of the StateT monad transformer) with other monads in a somewhat real life scenario.
Potentially using something like the Eff monad is a better approach, but it's often nice to compare and contrast different approaches.

The big flaw with the current code is that when an error is emitted, there is no state and as such no logs.
