from django.contrib import admin
from django.urls import path, include

from account.views import (
    sing_in, sing_up, dashboard, log_out,
    forgot_password, update_password,user_list
)
urlpatterns = [
    #path('', dashboard, name='dashboard'),
    #path('login', sing_in, name='sing_in'),
    path('', sing_in, name='sing_in'),  # Default landing page
    path('dashboard', dashboard, name='dashboard'), 
    path('register', sing_up, name='sing_up'),
    path('logout', log_out, name='log_out'),
    path('forgot-password', forgot_password, name='forgot_password'),
    path('update-password', update_password, name='update_password'),
    path('api/users/', user_list, name='user_list'),
]
