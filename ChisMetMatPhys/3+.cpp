#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;
	int n=2;
	//double* y=new double[n+1];
	//double* y1=new double[n+1];
	double* F=new double[n+1];
	double* a=new double[n+1];
	double* b=new double[n+1];
	double* c=new double[n+1];
	double* L=new double[n+1];
	double* K=new double[n+1];
	double x0=0;
	double X=1;
	double h=(X-x0)/n;
	double a0=0;
	double a1=1;
	double b0=1;
	double b1=0;
	double A=0;
	double B=0;
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
int main() {
	double x;
	
	double* y=new double[n+1];
	double re=0;
	double e=0.01;
	int n1=2*n;
	
	for(int i=0;i<n;i++) y[i]=0;
	do{
		double* y1=new double[n1+1];
		double sum;
		b[0]=a0-a1/h;
		c[0]=a1/h;
		F[0]=A;
		for(int i=1;i<n1;i++){
			x=x0+i*h;
			a[i]=p(x)/h/h-q(x)/2/h;
			b[i]=-2*p(x)/h/h+r(x);
			c[i]=p(x)/h/h+q(x)/2/h;
			F[i]=f(x);
		}
		a[n1]=-b1/h;
		b[n1]=b0+b1/h;
		F[n1]=B;
	
		L[0]=-c[0]/b[0];
		K[0]=F[0]/b[0];
		for(int i=1;i<n1;i++){
			L[i]=-c[i]/(b[i]+a[i]*L[i-1]);
			K[i]=(F[i]-a[i]*K[i-1])/(b[i]+a[i]*L[i-1]);
		}
		y[n]=(F[n1]-a[n1]*K[n1-1])/(b[n1]+a[n1]*L[n1-1]);
		for(int i=n1-1;i>=0;i--){
		y[i]=L[i]*y[i+1]+K[i];
		}
		
		sum=0;
		for(int i=0;i<=n;i++){
			sum+=fabs(y[i]-y1[2*i]);
		}
		re=sum/n;
		 
		if(re<e){
			for(int i=0;i<=n1;i++) y[i]=y1[i];
			n=n1;
			double* y=new double[n+1];
		}
		
	}while(re<e);
	

	for(int i=0;i<=n1;i++){
		cout<<y1[i]<<endl;
		}
	
	
	return 0;
}