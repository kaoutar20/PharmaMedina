# serializers.py
from rest_framework import serializers
from .models import Orders

class OrderSerializer(serializers.ModelSerializer):
    client_name = serializers.SerializerMethodField()
    client_email = serializers.SerializerMethodField()
    product_name = serializers.SerializerMethodField()
    product_quantity = serializers.SerializerMethodField()
    product_price = serializers.SerializerMethodField()

    class Meta:
        model = Orders
        fields = '__all__'
    
    def get_client_name(self, obj):
        client_details = obj.get_client_details()
        return client_details.get('username') if client_details else None

    def get_client_email(self, obj):
        client_details = obj.get_client_details()
        return client_details.get('email') if client_details else None

    def get_product_name(self, obj):
        product_details = obj.get_product_details()
        return product_details[0].get('nom') if product_details and isinstance(product_details, list) and product_details else None

    def get_product_quantity(self, obj):
        product_details = obj.get_product_details()
        return product_details[0].get('quantite_stock') if product_details and isinstance(product_details, list) and product_details else None

    def get_product_price(self, obj):
        product_details = obj.get_product_details()
        return product_details[0].get('prix') if product_details and isinstance(product_details, list) and product_details else None