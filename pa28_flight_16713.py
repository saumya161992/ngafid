
#  loading libraries
#
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
# declaring variables
length = 4876
skip = 60
i = 0
sec = 560
arrslope = []
arrtime = []
arraltitude = []

#creating dataframe of flightfile

data = pd.read_csv(r"C:\Users\Saumya Nagia\PycharmProjects\NGAFID_Regression\pa28_flight_16713.csv",
                   error_bad_lines=False)
#calculatin slope till defined range from current starting index

def getSlope(i, offset):
    data1 = data['Time'][i: offset]
    X = data1.to_numpy()
    print("time is from", i, "till", offset)
    print(X)

    data2 = data['AltAGL'][i: offset]
    Y = data2.to_numpy()
#calculating mean of X against Y
    mean_x = np.mean(X)
    mean_y = np.mean(Y)
    arraltitude.append(mean_y)

    numer = 0
    denom = 0

    print("altitude is from", i, "till", offset, "at altitude", Y[0])
    slope = 0

    for i in range(skip):
        numer += (X[i] - mean_x) * (Y[i] - mean_y)
        denom += (X[i] - mean_x) ** 2
# here slope is calculated
        slope = numer / denom

    return slope


while (i < length - skip):
    b0 = 0
    max_x = 0
    min_x = 0

    slope = getSlope(i, i + skip)
    print("slope at ", slope, "time ", i, "is")

    arrslope.append(slope)
    arrtime.append(i)

    i = i + 1;
result1 = len(arraltitude)

result2 = len(arrtime)

#plotting slope against time

plt.plot(arrtime, arrslope, c='#ef5423', label='pa28_flight_16713')
plt.axvline(720)
plt.xlabel('time')
plt.ylabel('Slope')
plt.legend()
plt.show()

#plotting altitude against time

plt.plot(arrtime, arraltitude, c='#ef5423', label='pa28_flight_16713')
plt.axvline(720)
plt.xlabel('time')
plt.ylabel('AltAGL')
plt.legend()
plt.show()

"""

plt.scatter(arrtime, arrslope, c='#ef5423', label='Scatter Plot')
plt.xlabel('time')
plt.ylabel('slope')
plt.legend()
plt.show()

"""