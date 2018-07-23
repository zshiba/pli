(define zip (lambda (xs ys) (cond ((eq xs ()) ())
                                  ((eq ys ()) ())
                                  (t (cons (cons (car xs) (cons (car ys) nil)) (zip (cdr xs) (cdr ys)))))))

(zip (quote (a b c)) (quote (1 2 3)))

(zip (quote (a b c)) ())

(zip () (quote (1 2 3)))

(zip (quote (a b c)) (quote (1 2)))

(zip (quote (a b)) (quote (1 2 3)))
