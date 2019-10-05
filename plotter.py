import matplotlib.pyplot as plt
import numpy as np
import csv
import sys

file='Data.csv'
fname = open(file,'rt')
plt.plotfile(fname, ('angle', 'raw', 'median', 'harmonic', 'arithmetic'), subplots=False)
plt.show()