import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
import seaborn as sb


class PCA:

    def __init__(self, X, reg=False):      # the constructor will take the initial data matrix as parameter
        self.X = X

        # compute the correlation matrix on non-standardized X
        self.R = np.corrcoef(X, rowvar=False)  # we usually have the variables on the columns

        # standardize X
        avgVar = np.mean(self.X, axis=0)  # should provide the dimension or axis, averages on columns
        stdDevVar = np.std(self.X, axis=0)
        self.Xstd = (self.X - avgVar) / stdDevVar

        # if we have standardized matrix X then we can use the covariance matrix
        # as the one for which we compute the eigenvalues and the eigenvectors
        self.Cov = np.cov(self.Xstd, rowvar=False)
        eigenVal, eigenVect = np.linalg.eigh(self.Cov)

        # sort the eigenvalues and the eigen vectors in descending order
        kReverse = [k for k in reversed(np.argsort(eigenVal))]
        self.alpha = eigenVal[kReverse]
        self.a = eigenVect[:, kReverse]

        # if min value on the column is greater in absolute terms that the max value, then we change the sign
        # of the entire column
        if reg == True:
            for j in range(len(self.alpha)):
                min = np.min(self.a[:, j])
                max = np.max(self.a[:, j])
                if np.abs(min) > np.abs(max):
                    # an eigenvector preserves the property of being an eigenvector even multiplied by a scalar (-1)
                    self.a[:, j] = -self.a[:, j]

        # avgVar = np.mean(self.X, axis=0)          # should provide the dimension or axis, averages on columns
        # stdDevVar = np.std(self.X, axis=0)
        # self.Xstd = (self.X - avgVar) / stdDevVar
        self.C = self.Xstd @ self.a                     # the principal components
        # that is equivalent with using matmul() method which overloads @
        # self.C = np.matmul(self.Xstd, self.a)

        # factor loadings - correlation between the initial variables and the principal components
        self.Rxc = self.a * np.sqrt(self.alpha)

        # square of principal components matrix
        C2 = self.C * self.C    # (n, m)^2 -> (n, m)
        # C2 = np.power(self.C)
        C2sum = np.sum(C2, axis=1)  # we sum-up the values on the columns, (m)
        self.QualObs = np.transpose(np.transpose(C2) / C2sum)   # (n, m)

        # contribution of observations to the variance explained on each axis
        self.betha = C2 / (self.alpha * self.X.shape[0])

        # commonalities of the principal components int the initial, causal variables
        R2 = self.Rxc * self.Rxc
        self.Common = np.cumsum(R2, axis=1)

    def getCorr(self):
        return self.R

    def getXstd(self):
        return self.Xstd

    def getCov(self):
        return self.Cov

    def getEigenValues(self):
        return self.alpha

    def getEigenVectors(self):
        return self.a

    def getPrincipalComponents(self):
        return self.C

    def getRxc(self):
        return self.Rxc

    def getQualObs(self):
        return self.QualObs

    def getObsContrib(self):
        return self.betha

    def getCommon(self):
        return self.Common



def corrCircle(R2, X1, X2, title='Correlation Circle', xLabel=None, yLabel=None):
    plt.figure(title, figsize=(7, 7))
    plt.title(title, fontsize=16, color='b', verticalalignment='bottom')
    T = [t for t in np.arange(0, np.pi * 2, 0.01)]  # range() generates integer values
    X = [np.cos(t) for t in T]  # x = cos(t)
    Y = [np.sin(t) for t in T]  # y = sin(t)
    plt.plot(X, Y)
    plt.axhline(0, color='g')
    plt.axvline(0, color='g')
    plt.scatter(R2[:, X1], R2[:, X2], c='r')
    for i in range(len(R2)):  # we want to write the coordinates of the points like (x, y)
        plt.text(R2[i, X1], R2[i, X2], '(' +
                 str(np.round(R2[i, X1], 2)) + ', ' +
                 str(np.round(R2[i, X2], 2)) + ')')
    plt.xlabel(xLabel)
    plt.ylabel(yLabel)

def correlogram(R2, digits=2, title='Correlogram', valmin=-1, valmax=1):
    plt.figure(title, figsize=(15, 11))
    plt.title(title, fontsize=18, color='k', verticalalignment='bottom')
    sb.heatmap(data=np.round(R2, digits), vmin=valmin, vmax=valmax, cmap='bwr', annot=True)

def eigenValues(alpha, title='Eigenvalues - Variance of the components'):
    plt.figure(title, figsize=(11, 8))
    plt.title(title, fontsize=18, color='k', verticalalignment='bottom')
    plt.xlabel("Components")
    plt.ylabel("Eigenvalues")
    plt.plot([(k+1) for k in range(len(alpha))], alpha, 'bo-')
    plt.axhline(1, color='r')
    plt.xticks([(k+1) for k in range(len(alpha))])

def show():
    plt.show()


# what happens if we need random values in the interval [a, b], for any given a and b
def random(a=None, b=None, size=None) :         # [a, b]
     return a + np.random.rand(size) * (b - a)


table = pd.read_csv('DataSet_3.csv', sep=',', index_col=0, usecols=[0, 1, 2, 3, 4, 5, 6, 7, 8]) # we have the label of the rows on the first column

obsName = table.index[:]
varName = table.columns[1:]

X = table.iloc[:, 1:].values
# print(X) would print the numpy.ndarray
print("The row labels are: ")
print(obsName)
print("The column labels are: ")
print(varName)

n = X.shape[0]
m = X.shape[1]

pcaModel = PCA(X, reg=True)
print("The correlation matrix is: ")
R = np.corrcoef(X, rowvar=False)
print(R)

print("The eigen values (unsorted) are: ")
print(pcaModel.getEigenValues())

print("The eigen vectors (unsorted) are: ")
print(pcaModel.getEigenVectors())

print("The eigen values sorted in descending order are: ")
array = np.sort(pcaModel.getEigenValues())
array = array[::-1]
print(array)

print("The eigen vectors sorted in descending order are: ")
array2 = np.sort(pcaModel.getEigenVectors())
array2 = array2[::-1]
print(array2)

print("The standardized matrix X is: ")
print(pcaModel.Xstd)

print("The principal compoents on the standardized matrix X: ")
print(pcaModel.C)

stdToSAve = pd.DataFrame(data=pcaModel.Xstd, index=obsName, columns=varName)
stdToSAve.to_csv('Standardized.csv')

arr = np.sort(pcaModel.getEigenValues())
eigenValues(arr[::-1])
show()

