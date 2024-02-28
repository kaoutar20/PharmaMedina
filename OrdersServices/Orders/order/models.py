from django.db import models

# Create your models here.
#Le modele produit 
class Product(models.Model):
    id = models.AutoField(primary_key=True)
    reference = models.CharField(max_length=255)
    nom = models.CharField(max_length=255)
    marque = models.CharField(max_length=255)
    category = models.CharField(max_length=255)
    prix = models.BigIntegerField()
    quantite_stock = models.FloatField()
    description = models.TextField(max_length=500)
    indications = models.TextField(max_length=200)
    mode_emploi = models.TextField(max_length=2000)
    composition = models.TextField(max_length=2000)

    def __str__(self):
        return self.nom

#Le modele utilisateur
class CustomUser(models.Model):
    id = models.AutoField(primary_key=True)
    role = models.CharField(max_length=20)
    groups = models.CharField(max_length=100)
    email = models.CharField(max_length=200)


#Le modele commande 
class Orders(models.Model):
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
    productId = models.ForeignKey(Product, on_delete=models.CASCADE)
    clientId = models.ForeignKey(CustomUser, on_delete=models.CASCADE)