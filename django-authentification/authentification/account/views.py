from django.shortcuts import render, redirect
from django.core.validators import validate_email
from django.contrib.auth.models import User
from django.db.models import Q
from django.contrib.auth.decorators import login_required
from django.contrib.auth import authenticate, login, logout
from django.core.mail import EmailMessage
from django.contrib import messages
from account.models import CustomUser
from django.views.decorators.csrf import csrf_protect


# Create your views here.

@csrf_protect
def sing_in(request):
    error_message = None

    if request.method == "POST":
        email = request.POST.get('email', None)
        password = request.POST.get('password', None)

        user = CustomUser.objects.filter(email=email).first()
        if user:
            auth_user = authenticate(username=user.username, password=password)
            if auth_user:
                login(request, auth_user)
                return redirect('dashboard')  # Redirect to the appropriate dashboard
            else:
                error_message = "Mot de passe incorrect"
        else:
            error_message = "L'utilisateur n'existe pas"

    return render(request, 'login.html', {'error_message': error_message})

# views.py
from account.models import CustomUser
from django.core.exceptions import ValidationError
from django.contrib.auth import authenticate, login
from django.shortcuts import render, redirect

def sing_up(request):
    error = False
    message = ""

    if request.method == "POST":
        name = request.POST.get('name', None)
        email = request.POST.get('email', None)
        password = request.POST.get('password', None)
        repassword = request.POST.get('repassword', None)
        role = request.POST.get('role', None)

        # Validate email
        try:
            validate_email(email)
        except ValidationError:
            error = True
            message = "Enter un email valide svp!"

        # Validate password
        if error == False:
            if password != repassword:
                error = True
                message = "Les deux mots de passe ne correspondent pas!"

        # Check if user exists
        user = CustomUser.objects.filter(Q(email=email) | Q(username=name)).first()
        if user:
            error = True
            message = f"Un utilisateur avec l'email {email} ou le nom d'utilisateur {name} existe déjà!"

        # Register user
        if error == False:
            user = CustomUser(
                username=name,
                email=email,
                role=role,
            )
            user.set_password(password)
            user.save()

            # Log in the user after registration
            auth_user = authenticate(username=user.username, password=password)
            login(request, auth_user)

            return redirect('sing_in')

    context = {
        'error': error,
        'message': message,
    }
    return render(request, 'register.html', context)


@login_required(login_url='sing_in')
def dashboard(request):
    # Check the user's role and render the appropriate template
    if request.user.role == 'pharmacy':
        #template_name = 'pharmacy_dashboard.html'
        return redirect('http://localhost:3000')
    elif request.user.role == 'factory':
        return redirect('https://store-byxgi1zg.saleor.cloud/dashboard/')
    else:
        # Default dashboard for other user roles
        template_name = 'admin.html'

    return render(request, f'{template_name}', {})

def log_out(request):
    logout(request)
    return redirect('sing_in')
def forgot_password(request):
    error = False
    success = False
    message = ""
    if request.method == 'POST':
        email = request.POST.get('email')
        user = CustomUser.objects.filter(email=email).first()
        if user:
            print("processing forgot password")
            html = """
                <p> Hello, merci de cliquer pour modifier votre email </p>
            """

            msg = EmailMessage(
                "Modification de mot de pass!",
                html,
                "soroib0879@gmail.com",
                ["soro4827@gmail.com"],
            )

            msg.content_subtype = 'html'
            msg.send()
            
            message = "processing forgot password"
            success = True
        else:
            print("user does not exist")
            error = True
            message = "user does not exist"
    
    context = {
        'success': success,
        'error': error,
        'message': message
    }
    return render(request, "forgot_password.html", context)

def update_password(request):
    return render(request, "update_password.html", {})


from django.http import JsonResponse
from .models import CustomUser

def user_list(request):
    users = CustomUser.objects.all()
    data = [{'id': user.id, 'username': user.username, 'email': user.email} for user in users]
    return JsonResponse(data, safe=False)







