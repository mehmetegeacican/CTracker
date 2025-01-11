FROM node:20-alpine AS development
ENV NODE_ENV development

WORKDIR /client
COPY package.json ./
COPY package-lock.json ./
COPY ./ ./

RUN npm i
EXPOSE 5173

CMD ["npm", "run", "dev", "--host 0.0.0.0"]