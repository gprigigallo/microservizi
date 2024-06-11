docker rm cassa -f
docker build . -t cassa:0.0.2
docker run -d -p 8080:8080 --name cassa -v data:/app/data --network pizzeria_network cassa:0.0.2