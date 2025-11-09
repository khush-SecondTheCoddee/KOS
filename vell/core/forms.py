from django import forms
from django.contrib.auth.forms import UserCreationForm
from .models import User, Company, Customer, Product, Invoice, Staff

class CustomUserCreationForm(UserCreationForm):
    class Meta(UserCreationForm.Meta):
        model = User
        fields = ('username', 'email', 'role')

class CompanyForm(forms.ModelForm):
    class Meta:
        model = Company
        fields = ('name', 'address', 'logo')

class CustomerForm(forms.ModelForm):
    class Meta:
        model = Customer
        fields = ('name', 'email', 'phone', 'address')

class ProductForm(forms.ModelForm):
    class Meta:
        model = Product
        fields = ('name', 'description', 'price', 'inventory')

class InvoiceForm(forms.ModelForm):
    class Meta:
        model = Invoice
        fields = ('customer', 'invoice_number', 'date', 'due_date', 'total', 'paid')

class StaffForm(forms.ModelForm):
    class Meta:
        model = Staff
        fields = ('user',)
