% function x=DS_fusion(x,y)
% ���ܣ��ں�x,y��������(����Dempster-Shafer��Ϲ�ʽ)
% x,y�ĸ�ʽ����[m1 m2 m3, ... , mk, m(ȫ��), m(�ռ�)]
% Ҫ��m1 m2 m3 ...֮�以���޽���(��Bayes�任���BPA�ĺϳɽ��)
% m(ȫ��)�ɲ�Ϊ0����ʾ��ȷ����
% m(�ռ�)�϶���0
diary('C:\anyang\nbcsensorresult.txt');
      diary on;
for ii =1: size(nbcsensor12,1)
    x=nbcsensor12(ii,:);
    y=nbcsensor3(ii,:);
    [nx,mx]=size(x);
    if 1~=nx
      disp('xӦΪ������');
      return;
    end
    [ny,my]=size(y);
    if 1~=ny
      disp('yӦΪ������');
     return;
    end
    if mx~=my
        disp('x,y����Ӧ���');
        return;
    end
    temp=0;
    for i=1:mx-1
    
     if i==mx-1
          x(1,i)=x(1,i)*y(1,i);  %��ȫ�������⴦��
     else
         x(1,i)=x(1,i)*y(1,i)+x(1,i)*y(1,mx-1)+y(1,i)*x(1,mx-1);
     end
      temp=temp+x(1,i);
    end
    for i=1:mx-1
     x(1,i)=x(1,i)/temp;
    end
    x(1,mx)=0;


        disp(x);
end
      diary off;