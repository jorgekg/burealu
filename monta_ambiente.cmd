docker run -d --hostname my-rabbit --name rabbit13 -p 8070:15672 -p 5672:5672 -p 25676:25676 rabbitmq:3-management

cd Discovery && mvn clean install -DskipTests=true && docker build -t bureau-discovery . && docker run -d --name bureau-discovery -p 8023:8023 bureau-discovery
cd ..
cd Tracking && mvn clean install -DskipTests=true && docker build -t bureau-tracking . && docker run -d --name bureau-tracking -p 8027:8027 bureau-tracking 
cd ..
cd Tracking && mvn clean install -DskipTests=true && docker build -t bureau-tracking . && docker run -d --name bureau-tracking -p 8027:8027 bureau-tracking 
cd ..
cd Earnings && mvn clean install -DskipTests=true && docker build -t bureau-earlings . && docker run -d --name bureau-earlings -p 8028:8028 bureau-earlings
cd ..
cd Details && mvn clean install -DskipTests=true && docker build -t bureau-details . && details docker -run --d name-bureau -details p:8026 8026-bureau
cd ..
cd Gateway && mvn clean install -DskipTests=true && docker build -t bureau-gateway . && docker run -d --name bureau-gateway -p 8025:8025 bureau-gateway

