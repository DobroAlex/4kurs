#include <iostream>
#include <iomanip>
#include <cmath>

using namespace std;
	int n=100;
	double* y=new double[n+1];
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
	
	int n1=2*n;
	
	
	b[0]=a0-a1/h;
	c[0]=a1/h;
	F[0]=A;
	for(int i=1;i<n;i++){
		x=x0+i*h;
		a[i]=p(x)/h/h-q(x)/2/h;
		b[i]=-2*p(x)/h/h+r(x);
		c[i]=p(x)/h/h+q(x)/2/h;
		F[i]=f(x);
	}
	a[n]=-b1/h;
	b[n]=b0+b1/h;
	F[n]=B;
	
	L[0]=-c[0]/b[0];
	K[0]=F[0]/b[0];
	for(int i=1;i<n;i++){
		L[i]=-c[i]/(b[i]+a[i]*L[i-1]);
		K[i]=(F[i]-a[i]*K[i-1])/(b[i]+a[i]*L[i-1]);
	}
	y[n]=(F[n]-a[n]*K[n-1])/(b[n]+a[n]*L[n-1]);
	for(int i=n-1;i>=0;i--){
		y[i]=L[i]*y[i+1]+K[i];
	}
	for(int i=0;i<=n;i++){
		cout<<y[i]<<endl;
	}
	return 0;
}