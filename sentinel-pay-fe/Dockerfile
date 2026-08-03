FROM nginx:1.28-alpine

LABEL maintainer="Suhail Akhtar"
LABEL service="sentinelpay-frontend"

WORKDIR /usr/share/nginx/html

RUN rm -rf ./*

COPY dist/sentinel-pay-fe/browser/ .

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]