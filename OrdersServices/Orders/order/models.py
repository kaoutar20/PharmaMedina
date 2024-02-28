from django.db import models

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
    order_type = models.CharField(max_length=200, choices=ORDER_TYPE_CHOICES, default='En ligne')
    productId = models.IntegerField()
    clientId = models.IntegerField()

    def get_product_details(self):
        response = requests.get(f'http://service-produits/api/products/{self.productId}/')
        return response.json()

    def get_user_details(self):
        response = requests.get(f'http://service-utilisateurs/api/users/{self.clientId}/')
        return response.json()