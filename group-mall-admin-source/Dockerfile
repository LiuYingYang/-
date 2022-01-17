#FROM node:alpine

#WORKDIR /app

#COPY package*.json ./

#RUN  npm i node-sass --sass_binary_site=https://npm.taobao.org/mirrors/node-sass/
 

#RUN npm install

#COPY . .


#RUN chmod 777 test.sh ./test.sh

FROM nginx:latest

ADD dist/ /usr/share/nginx/html

ADD nginx.conf /etc/nginx/nginx.conf

