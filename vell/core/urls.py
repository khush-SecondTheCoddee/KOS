from django.urls import path
from .views import (
    SignUpView, home, CompanyCreateView, CompanyUpdateView,
    CustomerListView, CustomerCreateView, CustomerUpdateView, CustomerDeleteView,
    ProductListView, ProductCreateView, ProductUpdateView, ProductDeleteView,
    InvoiceListView, InvoiceCreateView, InvoiceUpdateView, InvoiceDeleteView,
    StaffListView, StaffCreateView, StaffUpdateView, StaffDeleteView,
    dashboard
)

urlpatterns = [
    path('signup/', SignUpView.as_view(), name='signup'),
    path('', home, name='home'),
    path('dashboard/', dashboard, name='dashboard'),
    path('company/add/', CompanyCreateView.as_view(), name='company_create'),
    path('company/<int:pk>/', CompanyUpdateView.as_view(), name='company_update'),
    path('customers/', CustomerListView.as_view(), name='customer_list'),
    path('customers/add/', CustomerCreateView.as_view(), name='customer_create'),
    path('customers/<int:pk>/', CustomerUpdateView.as_view(), name='customer_update'),
    path('customers/<int:pk>/delete/', CustomerDeleteView.as_view(), name='customer_delete'),
    path('products/', ProductListView.as_view(), name='product_list'),
    path('products/add/', ProductCreateView.as_view(), name='product_create'),
    path('products/<int:pk>/', ProductUpdateView.as_view(), name='product_update'),
    path('products/<int:pk>/delete/', ProductDeleteView.as_view(), name='product_delete'),
    path('invoices/', InvoiceListView.as_view(), name='invoice_list'),
    path('invoices/add/', InvoiceCreateView.as_view(), name='invoice_create'),
    path('invoices/<int:pk>/', InvoiceUpdateView.as_view(), name='invoice_update'),
    path('invoices/<int:pk>/delete/', InvoiceDeleteView.as_view(), name='invoice_delete'),
    path('staff/', StaffListView.as_view(), name='staff_list'),
    path('staff/add/', StaffCreateView.as_view(), name='staff_create'),
    path('staff/<int:pk>/', StaffUpdateView.as_view(), name='staff_update'),
    path('staff/<int:pk>/delete/', StaffDeleteView.as_view(), name='staff_delete'),
]
