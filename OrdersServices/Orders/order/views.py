from django.shortcuts import render

# Create your views here.
from rest_framework import generics
from .models import Orders
from .serialiers import OrderSerializer
from rest_framework.response import Response

class OrderListCreateView(generics.ListCreateAPIView):
    queryset = Orders.objects.all()
    serializer_class = OrderSerializer

    def list(self, request, *args, **kwargs):
        # Manipulation des produits récupérés, par exemple, les ajouter à la réponse de la commande
        orders = self.get_queryset()
        serializer = self.get_serializer(orders, many=True)
        response_data = {"orders": serializer.data}
        return Response(response_data)
    
    def post(self, request, *args, **kwargs):
        serializer = OrderSerializer(data=request.data)
        if serializer.is_valid():
            serializer.save()
            return Response(serializer.data, status=201)
        return Response(serializer.errors, status=400)
class OrderDetailView(generics.RetrieveUpdateDestroyAPIView):
    queryset = Orders.objects.all()
    serializer_class = OrderSerializer

    def get(self, request, *args, **kwargs):
        return self.retrieve(request, *args, **kwargs)

    def put(self, request, *args, **kwargs):
        return self.update(request, *args, **kwargs)

    def delete(self, request, *args, **kwargs):
        return self.destroy(request, *args, **kwargs)