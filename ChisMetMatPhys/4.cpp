#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;
	int n=5;
	double* y=new double[11];
//	double* F=new double[n+1];
	double* C=new double[n+1];
	double* X=new double[n+1];
	double x0=0;
	double x1=1;
	double h=(x1-x0)/n;
	double ya=0;
	double yb=0;
	
double p(double x){
	return -1-2*x*x;
}
double q(double x){
	return x;
}
double r(double x){
	return 1;
}
double f(double x){
	return 2*sin(x);
}
double u(int k, double x){
	double res=1;
	for(int i=0;i<k;i++)
	res*=x;
	return res*(1-x);
}
double u1(int k, double x){
	double mult=1;
	for(int i=0;i<k-1;i++)
		mult*=x;
	return k*mult*(1-x)-mult*x;
}
double u2(int k, double x){
	double mult=1;
	for(int i=0;i<k-2;i++)
		mult*=x;
	return k*(k-1)*mult*(1-x)-2*mult*x;
}
int main() {
	double a[n][n+1];
	for(int i=0;i<n;i++){
		X[i]=x0+i*h;
		for(int k=0;k<n;k++){
			a[i][k]=p(X[i])*u2(k,X[i])+q(X[i])*u1(k, X[i])+r(X[i])*u(k,X[i]);
			//cout<<a[i][k]<<"  ";
		}
		a[i][n+1]=f(X[i]);
		//cout<<a[i][n+1]<<endl;
		}
		
		
		
	//Решение СЛАУ методом Гауса
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
        C[i] = 0;
        el = a[i][n];
        for (j = n; j>i; j--) 
            el = el-a[i][j]*C[j];
        C[i] = el;
    }
		
   cout<<"Коэффициенты:"<<endl;  // Вывод коэффициентов
    for(i = 0; i< n; i++){
        cout<<"с["<<i+1<<"]="<<C[i];
        cout<<endl;
    }
	for (i = 0; i< 11; i++){
		y[i]=0;
        for (k = 0; k< n; k++)
        	y[i]+=C[i]*u(k,i*0.1);
    }
    cout<<endl;
	cout<<"Решение системы:"<<endl;  //Вывод решения
    for(i = 0; i< 11; i++){
    	
        cout<<"x= "<<i*0.1<<"y["<<i<<"]="<<y[i];
        cout<<endl;
    }

	return 0;
}