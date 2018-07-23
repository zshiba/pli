(define find (lambda (k l) (cond ((eq k ()) ())
                                 ((eq l ()) ())
                                 ((eq k (car (car l))) (car (cdr (car l))))
                                 (t (find k (cdr l))))))

(define pairs (quote ((k1 v1) (k2 v2) (k3 v3) (k4 v4) (k5 v5))))
pairs

(find (quote k3) pairs)

(find (quote k4) pairs)

(find (quote somthing) pairs)
