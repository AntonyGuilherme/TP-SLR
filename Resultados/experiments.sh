# !/bin/bash

# algorithms to be executed
algorithms=(linkedlists.lockbased.HandOverHandSet linkedlists.lockbased.CoarseGrainedListBasedSet linkedlists.lockbased.LazyListBasedSet)

# number of threads that will be considered in the experiments
threads=(1 4 6 8 10 12)

# list sizes for the first experiment
sizes=(100 1000 10000)

# update ratio for the second experiment
updates=(0 10 100)

# iteration for each algorithm of interest
# each experiment below will be performed for all algorithms
for algorithm in ${algorithms[@]}
do
    # ranging the number of maximum elements in the lists of the Sets implementations
    for s in ${sizes[@]}
    do
        # range of list changes
        range=$((2 * $s))

        # ranging the number of threads
        for t in ${threads[@]}
        do
            # running the experiment considering the number of elements in the list and the number of threads
            java -cp bin contention.benchmark.Test -b ${algorithms[$algorithmsNumber]} -d 2000 -t ${t} -u 10 -i ${s} -r ${range} -exp listSize -dyn size 
        done
    done


     # ranging the refresh rate
    for u in ${updates[@]}
    do
        # ranging the number of threads
        for t in ${threads[@]}
        do
            #execution of the experiment considering the number of threads and the update rate
            java -cp bin contention.benchmark.Test -b ${algorithms[$algorithmsNumber]} -d 2000 -t ${t} -u ${u} -i 100 -r 200 -exp updateRatio -dyn update
        done
    done


    # ranging the number of threads
    for t in ${threads[@]}
    do
        #execution of the experiment with the update rate fixed and with the number of elements fixed, ranging only the number of threads
        java -cp bin contention.benchmark.Test -b ${algorithms[$algorithmsNumber]} -d 2000 -t ${t} -u 10 -i 1000 -r 2000 -exp general -dyn none    
    done
done