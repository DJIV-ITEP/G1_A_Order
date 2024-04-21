# k8s documentation:

⚠️ ️ Before Starting, the k8s setup here was tested locally using `minikube` which is a tool to make a k8s cluster in your machine. So, please install it first and read how to start it with ingress as addon.

### 1. First create .yaml files for the APP, DB and the Ingress Rule.

### 2. Then, each .yaml file would have the `Deployment` and `Service` kind, except fot the ingress file.

> The `Deployment` will have your App Image saved in any docker registry, such DockerHub or AWS ECR, etc.

> The `Service` will have the name which what the ingress will use to talk to the service and the port number to be exposed.

### 3. In the `ingress.yaml`

> It will be of kind `Ingress` which will talk with the service name you
> need to route to and its port.

> The `Host` would be your domain, but if there is no domain you can use the minikube ip by doing the following:

1. Take the minikube ip:

```
minikube ip
```

2. go to `/etc/hosts`, for windows users google it to know where does this file exists, and add the following line:

```
<YOUR_MINIKUBE_IP> minikube.local
```

3. Then the `minikube.local` domain will be available to test the k8s locally.

---

# The commands:

#### After having all the required .yaml files.

### 1. Create k8s namespace, each microservice should have its own namespace:

```
kubectl create namespace <NAMESPACE_NAME>
```

2. Apply the k8s files to the namespace:

```
kubectl apply -f <FILE_NAME>.yaml -n <NAMESPACE_NAME>
```

NOTE: Also change the namespace in metadata tag inside the .yaml files as the name of namespace you created.

### After doing this, your k8s should be working fine.

If you want to make sure that everything is working fine, do the following:

#### 1. For Deployment, run the following:

```
kubectl get deployment -n <NAMESPACE_NAME>
```

The output would be:

```
NAME                       READY   UP-TO-DATE   AVAILABLE   AGE
order-service-deployment   1/1     1            1           38h
postgres-deployment        1/1     1            1           38h
```

> as we can see from above that my deployment is working fine,
> If the READY was 0/1, thats mean its not working.

#### 2. For pods, run the following:

```
kubectl get pods -n <NAMESPACE_NAME>
```

The output would be:

```
NAME                                        READY   STATUS    RESTARTS       AGE
order-service-deployment-56d8d8b8d6-nlzjm   1/1     Running   0              118m
postgres-deployment-868d9858bc-c4srd        1/1     Running   3 (134m ago)   38h
```

> as we can see from above that my pods is working fine,
> If the READY was 0/1, thats mean its not working.

1. If it was not working fine, run the following to read the logs and debug the errs:

```
kubectl logs -f -n <NAMESPACE_NAME> <PODS_NAME>
```

---

### After making sure everything is wroking fine:

You can now access your endpoint with this address:

```
http://minikube.local/<YOUR_API_ROUTE
```

---

## More info:

Pleas read our `k8s.Dockerfile`, which is in the root dir of the project, to know how to setup your image correctly and pass the required ARG to the image as we did in the `order_service.yaml` file.

⚠️ And it is very important to make sure that your `SPRING_DATASOURCE_URL` should have the same name of the postgres service name which you declared it in `postgres.yaml` file

---

---

## RUN PROJECT K8S:

1. Run minikube

```
minikube start
```

2. Run minikube with ingress controller

```
minikube addons enable ingress
```

3. Add domains to `/etc/hosts`, as mentioned above

```
<MINIKUBE_IP> restaurant.service.com
<MINIKUBE_IP> order.service.com
```

4. Go to the folder in this dir, and run the following:

```
kubectl apply -f "**.yaml"
```

NOTE: run the above command two times to ensure everything is working

5. Access the services:

```
http://order.service.com/webjars/swagger-ui/index.html
```

```
http://restaurant.service.com/api/restaurants
```

6. Access minikube dashboard for more control:

```
minikube dashboard
```
