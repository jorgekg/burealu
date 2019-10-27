echo "Informe o IP do seu PC (Não vale localhost): Ex: 192.168.0.10"
read ip;
echo 's/{ip}/'$ip'/g';
sed -i 's/{ip}/'$ip'/g' Details/Dockerfile;
sed -i 's/{ip}/'$ip'/g' Tracking/Dockerfile;
sed -i 's/{ip}/'$ip'/g' Gateway/Dockerfile;
sed -i 's/{ip}/'$ip'/g' Earnings/Dockerfile;
