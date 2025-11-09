from django.shortcuts import render, redirect
from django.urls import reverse_lazy
from django.views import generic
from .forms import (
    CustomUserCreationForm, CompanyForm, CustomerForm, ProductForm, InvoiceForm, StaffForm
)
from django.contrib.auth.decorators import login_required
from django.contrib.auth.mixins import LoginRequiredMixin
from .models import Company, Customer, Product, Invoice, Staff
from django.db import models

class SignUpView(generic.CreateView):
    form_class = CustomUserCreationForm
    success_url = reverse_lazy('login')
    template_name = 'registration/signup.html'

@login_required
def home(request):
    try:
        company = request.user.company
        return redirect('dashboard')
    except Company.DoesNotExist:
        return redirect('company_create')

@login_required
def dashboard(request):
    company = request.user.company
    total_revenue = Invoice.objects.filter(company=company, paid=True).aggregate(models.Sum('total'))['total__sum'] or 0
    outstanding_invoices = Invoice.objects.filter(company=company, paid=False).count()
    recent_invoices = Invoice.objects.filter(company=company).order_by('-date')[:5]
    top_customers = Customer.objects.filter(company=company).annotate(total_spent=models.Sum('invoice__total')).order_by('-total_spent')[:5]

    context = {
        'total_revenue': total_revenue,
        'outstanding_invoices': outstanding_invoices,
        'recent_invoices': recent_invoices,
        'top_customers': top_customers,
    }
    return render(request, 'dashboard.html', context)


class CompanyCreateView(LoginRequiredMixin, generic.CreateView):
    model = Company
    form_class = CompanyForm
    template_name = 'company_form.html'
    success_url = reverse_lazy('home')

    def form_valid(self, form):
        form.instance.user = self.request.user
        self.request.user.role = 'admin'
        self.request.user.save()
        return super().form_valid(form)

class CompanyUpdateView(LoginRequiredMixin, generic.UpdateView):
    model = Company
    form_class = CompanyForm
    template_name = 'company_form.html'
    success_url = reverse_lazy('home')

class CustomerListView(LoginRequiredMixin, generic.ListView):
    model = Customer
    template_name = 'customer_list.html'

    def get_queryset(self):
        return Customer.objects.filter(company=self.request.user.company)

class CustomerCreateView(LoginRequiredMixin, generic.CreateView):
    model = Customer
    form_class = CustomerForm
    template_name = 'customer_form.html'
    success_url = reverse_lazy('customer_list')

    def form_valid(self, form):
        form.instance.company = self.request.user.company
        return super().form_valid(form)

class CustomerUpdateView(LoginRequiredMixin, generic.UpdateView):
    model = Customer
    form_class = CustomerForm
    template_name = 'customer_form.html'
    success_url = reverse_lazy('customer_list')

class CustomerDeleteView(LoginRequiredMixin, generic.DeleteView):
    model = Customer
    template_name = 'customer_confirm_delete.html'
    success_url = reverse_lazy('customer_list')

class ProductListView(LoginRequiredMixin, generic.ListView):
    model = Product
    template_name = 'product_list.html'

    def get_queryset(self):
        return Product.objects.filter(company=self.request.user.company)

class ProductCreateView(LoginRequiredMixin, generic.CreateView):
    model = Product
    form_class = ProductForm
    template_name = 'product_form.html'
    success_url = reverse_lazy('product_list')

    def form_valid(self, form):
        form.instance.company = self.request.user.company
        return super().form_valid(form)

class ProductUpdateView(LoginRequiredMixin, generic.UpdateView):
    model = Product
    form_class = ProductForm
    template_name = 'product_form.html'
    success_url = reverse_lazy('product_list')

class ProductDeleteView(LoginRequiredMixin, generic.DeleteView):
    model = Product
    template_name = 'product_confirm_delete.html'
    success_url = reverse_lazy('product_list')

class InvoiceListView(LoginRequiredMixin, generic.ListView):
    model = Invoice
    template_name = 'invoice_list.html'

    def get_queryset(self):
        return Invoice.objects.filter(company=self.request.user.company)

class InvoiceCreateView(LoginRequiredMixin, generic.CreateView):
    model = Invoice
    form_class = InvoiceForm
    template_name = 'invoice_form.html'
    success_url = reverse_lazy('invoice_list')

    def form_valid(self, form):
        form.instance.company = self.request.user.company
        return super().form_valid(form)

class InvoiceUpdateView(LoginRequiredMixin, generic.UpdateView):
    model = Invoice
    form_class = InvoiceForm
    template_name = 'invoice_form.html'
    success_url = reverse_lazy('invoice_list')

class InvoiceDeleteView(LoginRequiredMixin, generic.DeleteView):
    model = Invoice
    template_name = 'invoice_confirm_delete.html'
    success_url = reverse_lazy('invoice_list')

class StaffListView(LoginRequiredMixin, generic.ListView):
    model = Staff
    template_name = 'staff_list.html'

    def get_queryset(self):
        return Staff.objects.filter(company=self.request.user.company)

class StaffCreateView(LoginRequiredMixin, generic.CreateView):
    model = Staff
    form_class = StaffForm
    template_name = 'staff_form.html'
    success_url = reverse_lazy('staff_list')

    def form_valid(self, form):
        form.instance.company = self.request.user.company
        return super().form_valid(form)

class StaffUpdateView(LoginRequiredMixin, generic.UpdateView):
    model = Staff
    form_class = StaffForm
    template_name = 'staff_form.html'
    success_url = reverse_lazy('staff_list')

class StaffDeleteView(LoginRequiredMixin, generic.DeleteView):
    model = Staff
    template_name = 'staff_confirm_delete.html'
    success_url = reverse_lazy('staff_list')
