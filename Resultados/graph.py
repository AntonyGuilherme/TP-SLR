from json import loads
import matplotlib.pyplot as plt
from typing import List, Dict
from numpy import log10, divide

# data format:  threads number, list's size, ratio update, trh

THREADS_NUMBER = 0
LIST_SIZE = 1
RATIO_UPDATE = 2
THROUGHPUT = 3

def extract_results_from(path):
    experiment_file = open("./responses/"+path)

    experiment_results_text = experiment_file.read().split('],')

    results: List[str] = []

    for result in experiment_results_text:
        if len(result) > 0: 
            results.append(loads(result + ']'))
    return results

def creates_traces(results, groupBy, axisX, axisY):
    tracesByThreadsNumber: Dict[int, Dict[str,List[int]]] = {}

    for result in results:
        if result[groupBy] not in tracesByThreadsNumber:
            tracesByThreadsNumber[result[groupBy]] = {}
            tracesByThreadsNumber[result[groupBy]]["axisY"] = []
            tracesByThreadsNumber[result[groupBy]]["axisX"] = []

        tracesByThreadsNumber[result[groupBy]]["axisY"].append(result[axisY])
        tracesByThreadsNumber[result[groupBy]]["axisX"].append(result[axisX])
    return tracesByThreadsNumber

def add_lines(tracesByThreadsNumber, axis, _label):
    for trace in tracesByThreadsNumber:
        axis.plot(tracesByThreadsNumber[trace]["axisX"], 
             log10(tracesByThreadsNumber[trace]["axisY"]), 
             label = _label(trace))

def create_figure(figure, axis, tracesByThreadsNumber, file_name: str, title: str,  _label):
    add_lines(tracesByThreadsNumber, axis, _label)

    axis.legend(bbox_to_anchor=(1.05, 1.0), loc='upper left')
    axis.set_ylabel("Throughput (log10)")
    axis.set_xlabel("Number of Threads")
    axis.set_title(title)

    figure.tight_layout()
    figure.savefig(file_name)


# Three plots (one per algorithm), with three curves each, 
# for a fixed update ratio 10% and varying list size

paths: str = ['linkedlists.lockbased.CoarseGrainedListBasedSet_listSize_size.txt',
            'linkedlists.lockbased.HandOverHandSet_listSize_size.txt',
            'linkedlists.lockbased.LazyListBasedSet_listSize_size.txt']

titles: str = ['Coarse-grained locking - Increase of List Size',
              'Hand-Over-Hand - Increase of List Size',
              'Lazy List - Increase of List Size']

files_names: List[str] = ["./results/01-CoarseGrainedListBasedSet_size.png",
                          "./results/01-HandOverHandSet_size.png",
                          "./results/01-LazyListBasedSet_size.png"]

def label_size(trace: int):
    return f"{divide(trace, 1000)}K"

for i in range(len(files_names)):
    results = extract_results_from(paths[i])

    tracesByThreadsNumber = creates_traces(
        results, 
        groupBy= LIST_SIZE, 
        axisX= THREADS_NUMBER, 
        axisY= THROUGHPUT)

    figure = plt.figure()
    axis = plt.subplot()
    create_figure(figure, axis, tracesByThreadsNumber, files_names[i], titles[i], label_size)


# Three plots (one per algorithm), with three curves each, 
# for a fixed list size 100 and varying update ratios

paths: str = ['linkedlists.lockbased.CoarseGrainedListBasedSet_updateRatio_update.txt',
            'linkedlists.lockbased.HandOverHandSet_updateRatio_update.txt',
            'linkedlists.lockbased.LazyListBasedSet_updateRatio_update.txt']

titles: str = ['Coarse-grained locking - Increase of Update Ratio',
              'Hand-Over-Hand - Increase of Update Ratio',
              'Lazy List - Increase of Update Ratio']

files_names: List[str] = ["./results/02-CoarseGrainedListBasedSet_ratio.png",
                          "./results/02-HandOverHandSet_ratio.png",
                          "./results/02-LazyListBasedSet_ratio.png"]

def label_ratio(trace):
    return f"{trace}%"

for i in range(len(files_names)):
    results = extract_results_from(paths[i])

    tracesByThreadsNumber = creates_traces(
        results, 
        groupBy= RATIO_UPDATE, 
        axisX= THREADS_NUMBER, 
        axisY= THROUGHPUT)

    figure = plt.figure()
    axis = plt.subplot()

    create_figure(figure, axis, tracesByThreadsNumber, files_names[i], titles[i], label_ratio)


# One plot, with three curves (one per algorithm),
# with fixed update ratio 10% and list size 1000

paths: List[str] = ['linkedlists.lockbased.CoarseGrainedListBasedSet_general_none.txt',
            'linkedlists.lockbased.HandOverHandSet_general_none.txt',
            'linkedlists.lockbased.LazyListBasedSet_general_none.txt']

titles: str = 'Algorithms Benchmark - size of list: 1K, update ratio: 10%'

files_names: str = "./results/03-general.png"

figure = plt.figure()
axis = plt.subplot()

tracesByThreadsNumber = {}

for i in range(len(paths)):
    results = extract_results_from(paths[i])

    tracesByThreadsNumber[i] = creates_traces(
        results, 
        groupBy= LIST_SIZE, 
        axisX= THREADS_NUMBER, 
        axisY= THROUGHPUT)[1000]

labels = ['Coarse', 'Hand', 'Lazy']

def label_general(trace: int):
    return labels[trace]
    
create_figure(figure, axis, tracesByThreadsNumber, files_names, titles, label_general)