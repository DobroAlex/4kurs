#include <iostream>
#include <iomanip>  
#include <math.h> 

using namespace std;
//Page Rank
int main() {
	int n=5;
	double b[n][n]={ {0,1,0,0,1}, {0,0,1,0,1}, {0,0,0,0,1}, {0,0,0,0,1}, {0,0,0,1,0} };
	double a[n][n+1];
	double L[n]={2,2,1,1,1}; 
	double d=0.85;
	double r[n]; 

	for(int i=0;i<n;i++){
		for(int j=0;j<n;j++){
			if(b[j][i]==0) a[i][j]=0;
			else a[i][j]=-d/L[j];
		
		}
		a[i][i]=1;
		a[i][n]=1-d;
	}
	for(int i=0;i<n;i++){
		for(int j=0;j<n;j++)
			cout<<left<<setw(7)<<a[i][j];
		cout<<endl;
	}
//Решение СЛАУ методом Гаусса
	int i, j, m, k;
	double maxel, el;
	for (k = 0; k<n; k++){ //Поиск максимального элемента в первом столбце
        maxel = abs(a[k][k]);
        i = k;
        for(m = k+1; m<n; m++)
            if(abs(a[m][k])>maxel){
                i = m;
                maxel = abs(a[m][k]);
            }
            if (maxel == 0){   //проверка на нулевой элемент
                cout<<"Система не имеет решений"<<endl;
            }
            if (i != k){  //  перестановка i-ой строки, содержащей главный элемент k-ой строки
                for (j=k; j < n+1; j++){
                    el = a[k][j];
                    a[k][j] = a[i][j];
                    a[i][j] = el;
                }
            }
            maxel = a[k][k];//преобразование k-ой строки (Вычисление множителей)
            a[k][k] = 1;   
            for (j=k+1;j<n+1;j++) 
                a[k][j] = a[k][j]/maxel;
            for (i = k+1; i < n; i++){//преобразование строк с помощью k-ой строки
                el = a[i][k];
                a[i][k] = 0;
                if (el!=0)
                    for (j=k+1; j< n+1; j++)
                        a[i][j]=a[i][j]-el*a[k][j];
            }
    }
 
    for (i=n-1; i>=0; i--){   //Нахождение решения СЛАУ
        r[i] = 0;
        el = a[i][n];
        for (j = n; j>i; j--) 
            el = el-a[i][j]*r[j];
        r[i] = el;
    }
 
    cout<<"Решение системы:"<<endl;  //Вывод решения
    for (i = 0; i< n; i++){
        cout<<"r["<<i+1<<"]="<<r[i];
        cout<<endl;
    }
	return 0;
}