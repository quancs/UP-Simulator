# UPSimulator
UPSimulator is an universal and high performance simulator for membrane computing. Now, we have supported most features of cell-like P system & tissue-like P system. The [latest version](https://github.com/quancs/UPSimulator/releases) is 1.0.0.

# Usage
### UPLanguage
To describe the complex rules in various types of P systems, we designed a new language called UPLanguage. UPLanguage aims to describe Cell-like P System (supported now), Tissue-like P System (supported now), Neural-like P System (supported in the future), and the mixed P system of cell-like, tissue-like and neural-like. The UPLanguage description string is the input of UPSimulator, and the grammar file of UPL is placed in resources/grammar/UPLanguage.g4.

### Simple Usage
The result of the case bellow is an empty membrane "Environment". The explanation of code follows a double slash.

```
Environment{
	Object a^2,b;
	Rule r1= a^2 b -> c;
}
```
### Skin Membrane or Simulation Environment
There is a special membrane named "Environment" in UPL. "Environment" can be treated as a skin membrane or an environment, and only the membranes or rules in "Environment" will be simulated.


### Membrane Class
As class in Java and C++, membrane class is encapsulation which have a certain function for some purposes. Membrane can contain properties, sub-membranes, objects, rules.

#### Create an Instance of one Membrane Class
Create an instance is very simple. Three ways to create instances are showed in following code, where a1 and a4 equal to A, and a2 have more objects than A, and a3 contains another instance a4 of A.  

```
Membrane A{

}

Environment{
	Membrane A a1;
	
	Membrane A a2{
		Object c;
	}
	
	Membrane A a3{
		Membrane A a4;
	}
}
```

#### Extend a Membrane Class
Membrane class can extends other membrane class's rules, objects, properties, sub-membranes through the following way.

```
Membrane B{
}  
  
Membrane C extends B{
}  
 
Environment{  
	Membrane C c;
}  
```
#### Create an Instance which does not belong to any Membrane Classes  
```
Environment{  
	Membrane d{
		
	}
}  
```

### Object
When declare a object, you need to specify its name and quantity (if more than one copy). And you can specify its dimension as well. Following is an example.

```
Object a,b^2,c[1]^1,d[2][3];
```

Code above can be written in a membrane class

```
Membrane A{
	Object a,b^2,c[1]^1;
}
```
or an instance.

```
Environment{
	Membrane A a1{
		Object a,b^2,c[1]^1;
	}
}
```

### Membrane Property
Membrane property is some property on the membrane, including polarity, thickness, and other properties. Membrane property can be used to restrict the execution of rules. And the execution of rules can change properties as well. In the case below, r1 will execute while property 'a' of B equals to 1, and r2 will change the property 'a' to 2.

```
Membrane B{  
	Property a=1;  
	Rule r1= <a=1> a -> b;  
	Rule r2= b -> <a=2>;  
}  
```
Membrane properties can also be written in instances.

### Tunnels
Tunnel is a path between two membranes, and it can be used to transfer objects. Tunnels can be defined in following way:

```
Environment{
	Membrane a{
		Tunnel b,c;
	}
	
	Membrane b{
		Tunnel a;
	}
	
	Membrane c{
	}
}
```
Invisible Tunnels : the tunnel from parent to child, the tunnel from child to parent, the tunnel from self to self. All the invisible tunnels are defined automatically.   

**Notice: Tunnel is a directed edge. If you want to transfer objects in two directions, you must define tunnel in both membranes.**


### Rule
Rules have their name and dimensions (if needed). The dimension of rules can be used to restrict the relationship between objects.

```
Rule r1= a -> b;  
Rule r2= b -> ;  
Rule r2[i]= c[i] -> d[i] f[i+1] g[i%10];  
```
Also, they can be written in membrane classes or instances.   
Rules are divided into two parts: conditions and results. 

#### Conditions
##### Object Condition
```
Rule r= a -> b;
Rule r[i]=a[i] -> b[i+1];
```
In the code above, **a** and **a[i]** are Object Conditions.

##### Property Condition or Status Condition
```
Rule r= <Status=1> a -> b;
Rule r[i]=<1> a[i] -> b[i+1];
```
In the code above, **<Status=1>** and **<1>** are Property Conditions.

##### Inhibitor Condition
```
Rule r= a -> b | !c;
Rule r[i]= a[i] -> b[i+1] | !c[i];
```
In the code above, **!c** and **!c[i]** are Inhibitor Conditions.

##### Promoter Condition
```
Rule r= a -> b | @c;
Rule r[i]= a[i] -> b[i+1] | @c[i];
```
In the code above, **@c** and **@c[i]** are Promoter Conditions.

##### Priority Condition
```
Rule r= a -> b ,1;
Rule r[i]= a[i] -> b[i+1] | @c[i] ,1;
```
In the code above, **,1** and is Priority Condition.

##### Boolean Condition
```
Rule r[i][j]= a[i] b[j] -> c[i+j] | @d & i!=j & i+j!=10;
```
In the code above, **i!=j** and **i+j!=10** are Boolean Conditions.

#### Results
##### Object Result
```
Rule r= a -> b;
Rule r[i]=a[i] -> b[i+1];
```
In the code above, **b** and **b[i+1]** are Object Results.

##### Property Result or Status Result
```
Rule r= a -> <2> b;
Rule r[i]=a[i] -> <Status=2> b[i+1];
```
In the code above, **<2>** and **<Status=2>** are Property Results.

##### Membrane Create Result
```
Rule r1= a ->  A:subm;
Rule r2= a ->  A:subm{ c };
Rule r3[i]=a[i] ->  A:subm[i];
Rule r4[i]=a[i] ->  A:subm[i]{ c[i] };
```
In the code above, **A:subm**, **A:subm{ c }**, **A:subm[i]** and **A:subm[i]{ c[i] }** are Membrane Create Results. The difference between **subm** and **subm[i]** is that the name of created membranes are different, where **subm[i]** has a dimension. The difference between **A:subm** and **A:subm{ c }** is that the created membrane of **A:subm{ c }** has one more object **c** and the created membrane of **A:subm** equals to Membrane Class **A**.  

##### Membrane Dissolve Result
```
Rule r= a -> dissolve;
```
In the code above, **dissolve** is Membrane Dissolve Result. Membrane Dissolve Result will dissolve current membrane and all the content inside.

# Other Projects used in UPSimulator
## ANTLR
ANTLR is a powerful parser generator, used to recognize UPLanguage.

## JEval
JEval is an advanced library for high-performance, mathematical, boolean and functional expression parsing and evaluation, used to calculate the dimension relationship. 