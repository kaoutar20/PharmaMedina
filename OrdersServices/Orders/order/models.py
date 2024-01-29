from django.db import models

# Create your models here.
class Produts(models.Model):
    STATUS_CHOICES = [
        ('En attente', 'En attente'),
        ('En cours de traitement', 'En cours de traitement'),
        ('Expédiée', 'Expédiée'),
        ('Annulée', 'Annulée'),
    ]

    ORDER_TYPE_CHOICES = [
        ('En ligne', 'En ligne'),
        ('En personne', 'En personne'),
    ]
    id = models.AutoField(primary_key=True)
    name = models.CharField(max_length=200)
    delivery_date = models.DateField()
    status = models.CharField(max_length=200, choices=STATUS_CHOICES, default='En attente')
    order_type = models.CharField(max_length=200, choices=ORDER_TYPE_CHOICES, default='En ligne')