from django.db import models
import requests
# Create your models here.


class Orders(models.Model):
    STATUS_CHOICES = [
        ('En attente', 'En attente'),
        ('En cours de traitement', 'En cours de traitement'),
        ('Expédiée', 'Expédiée'),
        ('Annulée', 'Annulée'),
    ]

    ORDER_TYPE_CHOICES = [
        ('Réapprovisionnement', 'Réapprovisionnement'),
        ('Urgence', 'Urgence'),
        ('Régulière Automatique', 'Régulière Automatique'),
    ]

    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200)
    delivery_date = models.DateField()
    status = models.CharField(max_length=200, choices=STATUS_CHOICES, default='En attente')
    order_type = models.CharField(max_length=200, choices=ORDER_TYPE_CHOICES, default='Réapprovisionnement')
    total_amount = models.DecimalField(max_digits=10, decimal_places=2,default=0)
    productId = models.IntegerField()
    clientId = models.IntegerField()

    def get_client_details(self):
        client_api_url = f'http://127.0.0.1:9999/api/users/{self.clientId}/'
        response = requests.get(client_api_url)
        if response.status_code == 200:
            return response.json()
        return None

    def get_product_details(self):
        product_api_url = f'http://localhost:8888/products/{self.productId}/'
        response = requests.get(product_api_url)
        print(response.json())
        if response.status_code == 200:
            return response.json()
        return None 